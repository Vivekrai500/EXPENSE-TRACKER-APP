
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.room.entities.ExpenseEntity
import com.vivek.expensetrackerapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesByCategoryUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    operator fun invoke(expenseCategoryId:String): Flow<List<ExpenseEntity>> {
        return repository.getExpensesByCategory(categoryId = expenseCategoryId)
    }



}