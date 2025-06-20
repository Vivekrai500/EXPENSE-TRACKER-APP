
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(dates:List<String>,categoryId:String): Flow<List<TransactionEntity>> {
        return repository.searchTransactions(dates = dates,categoryId = categoryId)

    }
}