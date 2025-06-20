
package com.vivek.expensetrackerapp.presentation.bottomsheets.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.core.util.UiEvent
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import com.vivek.expensetrackerapp.domain.use_case.CreateExpenseCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


data class AddExpenseCategoryFormState(
    val expenseCategoryName: String = "",
    val isLoading: Boolean = false,

    )

@HiltViewModel
class AddExpenseCategoryScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val createExpenseCategoryUseCase: CreateExpenseCategoryUseCase,
) : ViewModel() {

    val _formState = MutableStateFlow(AddExpenseCategoryFormState())
    val formState = _formState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeExpenseName(text: String) {
        _formState.value = _formState.value.copy(expenseCategoryName = text)

    }


    fun addExpenseCategory() {
        viewModelScope.launch {
            if (_formState.value.expenseCategoryName.isBlank()) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Expense Category Name cannot be black"))
            } else {
                val expenseCategory = ExpenseCategory(
                    expenseCategoryName = _formState.value.expenseCategoryName,
                    expenseCategoryId = UUID.randomUUID().toString(),


                    )
                createExpenseCategoryUseCase(expenseCategory = expenseCategory).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _formState.value = _formState.value.copy(isLoading = true)

                        }

                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = result.data?.msg
                                        ?: "Expense Category added successfully"
                                )
                            )
                            _formState.value = _formState.value.copy(
                                expenseCategoryName = "",
                                isLoading = false
                            )

                        }

                        is Resource.Error -> {
                            _formState.value = _formState.value.copy(
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = result.message ?: "An unexpected  error occurred"
                                )
                            )

                        }
                    }
                }.launchIn(this)


            }
        }

    }


}