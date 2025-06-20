
package com.vivek.expensetrackerapp.presentation.bottomsheets.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.core.util.UiEvent
import com.vivek.expensetrackerapp.core.util.generateFormatDate
import com.vivek.expensetrackerapp.core.util.isNumeric
import com.vivek.expensetrackerapp.core.util.localDateTimeToDate
import com.vivek.expensetrackerapp.domain.models.Income
import com.vivek.expensetrackerapp.domain.models.Transaction
import com.vivek.expensetrackerapp.domain.use_case.CreateIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

data class AddIncomeFormState(
    val incomeName:String = "",
    val incomeAmount:Int = 0,
)


@HiltViewModel
class AddIncomeScreenViewModel @Inject constructor(
    private val createIncomeUseCase: CreateIncomeUseCase,
) :ViewModel() {

    val _formState = MutableStateFlow(AddIncomeFormState())
    val formState = _formState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeIncomeAmount(text: String) {
        if (text.isBlank()) {
            _formState.value = _formState.value.copy(incomeAmount = 0)
            return
        }
        if (isNumeric(text)) {
            _formState.value = _formState.value.copy(incomeAmount =text.toInt())
        } else {
            _formState.value = _formState.value.copy(incomeAmount = 0)
        }
    }

    fun onChangeIncomeName(text: String) {
        _formState.value = _formState.value.copy(incomeName = text)

    }

    fun addIncome() {
        viewModelScope.launch {
            if (_formState.value.incomeName.isBlank()) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Income Name cannot be black"))
                return@launch
            }
            if (_formState.value.incomeAmount == 0 ) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Income Amount cannot be 0"))
                return@launch
            }
            val income = Income(
                incomeName= formState.value.incomeName,
                incomeAmount = _formState.value.incomeAmount,
                incomeId = UUID.randomUUID().toString(),
                incomeCreatedAt = generateFormatDate(date = LocalDate.now()) ,
            )
            createIncomeUseCase(income = income).onEach { result ->
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.data?.msg ?: "Transaction added successfully"
                            )
                        )
                        _formState.value = _formState.value.copy(
                            incomeName = "",
                            incomeAmount = 0
                        )
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.message
                                    ?: "An error occurred creating this transaction"
                            )
                        )
                    }
                }
            }.launchIn(this)
        }

    }
}