
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class GetAllExpenseCategoriesUseCase @Inject constructor(
    private val repository: ExpenseCategoryRepository
) {

    operator fun invoke():Flow<List<ExpenseCategoryEntity>>{
        return repository.getAllExpenseCategories()
    }
}