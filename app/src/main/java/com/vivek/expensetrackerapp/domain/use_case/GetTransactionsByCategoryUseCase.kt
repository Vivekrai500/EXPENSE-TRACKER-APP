
package com.vivek.expensetrackerapp.domain.use_case


import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.core.util.FilterConstants
import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsByCategoryUseCase @Inject constructor(
    private val getFilteredTransactionsUseCase: GetFilteredTransactionsUseCase,
    private val repository: TransactionRepository
) {
    operator fun invoke(categoryId: String): Flow<List<TransactionEntity>> {
        if (categoryId == FilterConstants.ALL){
            return getFilteredTransactionsUseCase(filter = FilterConstants.ALL)
        }else{
            return repository.getTransactionByCategory(categoryId = categoryId)
        }
    }
}