
package com.vivek.expensetrackerapp.presentation.bottomsheets.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.core.util.UiEvent
import com.vivek.expensetrackerapp.core.util.generateFormatDate
import com.vivek.expensetrackerapp.core.util.isNumeric
import com.vivek.expensetrackerapp.core.util.localDateTimeToDate
import com.vivek.expensetrackerapp.domain.models.Expense
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import com.vivek.expensetrackerapp.domain.use_case.CreateExpenseUseCase
import com.vivek.expensetrackerapp.domain.use_case.GetAllExpenseCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

data class AddExpenseFormState(
    val expenseName: String = "",
    val expenseAmount: Int = 0,
    val selectedExpenseCategory: ExpenseCategory? = null,
    val isLoading: Boolean = false,
    )
@HiltViewModel
class AddExpenseScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllExpenseCategoriesUseCase: GetAllExpenseCategoriesUseCase,
    private val createExpenseUseCase: CreateExpenseUseCase,

    ) : ViewModel() {

    val _formState = MutableStateFlow(AddExpenseFormState())
    val formState = _formState.asStateFlow()

    val expenseCategories = getAllExpenseCategoriesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeExpenseName(text: String) {
        _formState.value = _formState.value.copy(expenseName = text)
    }

    fun onChangeExpenseAmount(text: String) {
        if (text.isBlank()) {
            _formState.value = _formState.value.copy(expenseAmount = 0)

            return
        }
        if (isNumeric(text)) {
            _formState.value = _formState.value.copy(expenseAmount = text.toInt())


        } else {

            _formState.value = _formState.value.copy(expenseAmount = 0)
        }
    }

    fun onChangeSelectedExpenseCategory(category: ExpenseCategory) {
        _formState.value = _formState.value.copy(selectedExpenseCategory = category)
    }

    fun addExpense() {
        viewModelScope.launch {
            if (_formState.value.expenseAmount == 0 || _formState.value.expenseName == "") {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        uiText = "Please enter a valid expense"
                    )
                )
                return@launch
            }
            if (_formState.value.selectedExpenseCategory == null) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        uiText = "Please select an expense category"
                    )
                )
                return@launch
            }
            val newExpense = Expense(
                expenseId = UUID.randomUUID().toString(),
                expenseName = _formState.value.expenseName,
                expenseAmount = _formState.value.expenseAmount,
                expenseCategoryId = _formState.value.selectedExpenseCategory!!.expenseCategoryId,
                expenseCreatedAt = SimpleDateFormat("hh:mm:ss").format(Date()),
                expenseUpdatedAt = SimpleDateFormat("hh:mm:ss").format(Date()),
                expenseCreatedOn = generateFormatDate(date = LocalDate.now()),
                expenseUpdatedOn = generateFormatDate(date = LocalDate.now())
            )
            createExpenseUseCase(expense = newExpense).onEach { result ->
                when (result) {
                    is Resource.Loading -> {

                        _formState.value = _formState.value.copy(isLoading = true)

                    }

                    is Resource.Success -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.data?.msg ?: "Expense  added successfully"
                            )
                        )
                        _formState.value = _formState.value.copy(
                            expenseName = "",
                            expenseAmount = 0,
                            selectedExpenseCategory = null,
                            isLoading = false,
                        )
                    }

                    is Resource.Error -> {
                        _formState.value = _formState.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.message ?: "An error occurred"
                            )
                        )

                    }
                }
            }.launchIn(this)
        }
    }
}