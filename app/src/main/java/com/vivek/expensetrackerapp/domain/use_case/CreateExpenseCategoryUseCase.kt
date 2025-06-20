
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.core.util.Response
import com.vivek.expensetrackerapp.domain.models.Expense
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import com.vivek.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class CreateExpenseCategoryUseCase @Inject constructor(
    private val repository: ExpenseCategoryRepository
){

    suspend operator fun invoke(expenseCategory: ExpenseCategory): Flow<Resource<Response>> = flow {
        try {
            emit(value = Resource.Loading())
            val existingExpenseCategory = repository.getExpenseCategoryByName(name = expenseCategory.expenseCategoryName)
            if (existingExpenseCategory == null){
                repository.saveExpenseCategory(expenseCategory = expenseCategory)
                emit(value = Resource.Success(Response(msg = "Expense Category Added")))
            }else{
                emit(value = Resource.Error(message = "An expense category with a similar name already exists"))
            }
        }catch (e:IOException){
            emit(value = Resource.Error(message = e.localizedMessage?: "An unexpected error occurred"))
        }
    }
}