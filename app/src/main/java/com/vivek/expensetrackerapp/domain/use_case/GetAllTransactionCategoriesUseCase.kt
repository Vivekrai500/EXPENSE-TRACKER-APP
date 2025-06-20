
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.vivek.expensetrackerapp.domain.repository.TransactionCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTransactionCategoriesUseCase @Inject constructor(
    private val repository: TransactionCategoryRepository
){

    operator fun invoke(): Flow<List<TransactionCategoryEntity>> {
        return repository.getAllTransactionCategories()
    }
}