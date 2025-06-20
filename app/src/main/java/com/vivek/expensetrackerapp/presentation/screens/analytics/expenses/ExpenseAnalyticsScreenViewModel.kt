
package com.vivek.expensetrackerapp.presentation.screens.analytics.expenses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.expensetrackerapp.domain.models.ExpenseCategoryInfo
import com.vivek.expensetrackerapp.domain.models.ExpenseCategoryInfoEntity
import com.vivek.expensetrackerapp.domain.toExternalModel
import com.vivek.expensetrackerapp.domain.use_case.GetAllExpenseCategoriesUseCase
import com.vivek.expensetrackerapp.domain.use_case.GetExpensesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ExpensesAnalyticsScreenUiState {
    object Loading:ExpensesAnalyticsScreenUiState
    data class Success(val expenseCategories :List<ExpenseCategoryInfo>):ExpensesAnalyticsScreenUiState

    data class Error(val message:String):ExpensesAnalyticsScreenUiState
}

@HiltViewModel
class ExpenseAnalyticsScreenViewModel @Inject constructor(
    private val getAllExpenseCategoriesUseCase: GetAllExpenseCategoriesUseCase,
    private val getExpensesByCategoryUseCase: GetExpensesByCategoryUseCase,

    ) : ViewModel() {

    private val _expenseCategories = mutableStateOf<List<ExpenseCategoryInfoEntity>>(emptyList())
    val expenseCategories: State<List<ExpenseCategoryInfoEntity>> = _expenseCategories


    init {
        viewModelScope.launch {
            getAllExpenseCategoriesUseCase().collect { expenseCategoryEntities ->
                val expenseCategories =
                    expenseCategoryEntities.map { entity -> entity.toExternalModel() }
                val expenseCategoriesInfo = expenseCategories.map {
                    val expenses = getExpensesByCategoryUseCase(it.expenseCategoryId)
                    ExpenseCategoryInfoEntity(
                        expenseCategory = it,
                        expenses = expenses
                    )
                }
                _expenseCategories.value = expenseCategoriesInfo

            }
        }

    }


}