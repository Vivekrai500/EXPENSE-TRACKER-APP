
package com.vivek.expensetrackerapp.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.expensetrackerapp.core.util.UiEvent
import com.vivek.expensetrackerapp.core.util.datesBetween
import com.vivek.expensetrackerapp.core.util.generateFormatDate
import com.vivek.expensetrackerapp.domain.models.Transaction
import com.vivek.expensetrackerapp.domain.models.TransactionCategory
import com.vivek.expensetrackerapp.domain.toExternalModel
import com.vivek.expensetrackerapp.domain.use_case.GetAllTransactionCategoriesUseCase
import com.vivek.expensetrackerapp.domain.use_case.SearchTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class SearchScreenState(
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val transactionCategory: TransactionCategory? = null,
    val transactions: List<Transaction> = emptyList(),

    )


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getAllTransactionCategoriesUseCase: GetAllTransactionCategoriesUseCase,
    private val searchTransactionsUseCase: SearchTransactionsUseCase,

    ) : ViewModel() {
    val transactionCategories = getAllTransactionCategoriesUseCase()

    private val _uiState = MutableStateFlow(SearchScreenState())
    val uiState = _uiState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeTransactionStartDate(date: LocalDate) {
        _uiState.update { it.copy(startDate = date) }
    }

    fun onChangeTransactionEndDate(date: LocalDate) {
        _uiState.update { it.copy(endDate = date) }
    }

    fun onChangeTransactionCategory(category: TransactionCategory) {
        _uiState.update { it.copy(transactionCategory = category) }
    }


    fun searchTransactions() {
        viewModelScope.launch {

            if (_uiState.value.transactionCategory == null) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Please select a transaction category"))
                return@launch
            }
            val datesInBetween = datesBetween(
                startDate = generateFormatDate(_uiState.value.startDate),
                endDate = generateFormatDate(_uiState.value.endDate)
            )
            searchTransactionsUseCase(
                dates = datesInBetween,
                categoryId = _uiState.value.transactionCategory!!.transactionCategoryId
            ).collect { transactions ->
                _uiState.update { initialState -> initialState.copy(transactions = transactions.map { it.toExternalModel() }) }

            }
        }
    }


}