
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.core.util.Response
import com.vivek.expensetrackerapp.domain.models.Expense
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import com.vivek.expensetrackerapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class CreateExpenseUseCase @Inject constructor(
  private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Flow<Resource<Response>> = flow {
        try {
            emit(Resource.Loading())
            repository.createExpense(expense = expense)
            emit(Resource.Success(data = Response(msg = "Expense saved successfully")))
        }catch (e:IOException){
            emit(Resource.Error(message = e.localizedMessage?: "An unexpected error occurred"))
        }
    }
}