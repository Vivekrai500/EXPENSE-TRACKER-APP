
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transactionId:String){
        return repository.deleteTransactionById(transactionId = transactionId)
    }

}