
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.room.entities.ExpenseEntity
import com.vivek.expensetrackerapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val repository:ExpenseRepository,
) {
    operator fun invoke(): Flow<List<ExpenseEntity>> {
        return repository.getAllExpenses()
    }
}