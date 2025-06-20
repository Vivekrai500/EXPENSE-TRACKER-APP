
package com.vivek.expensetrackerapp.domain.use_case


import com.vivek.expensetrackerapp.domain.models.Transaction
import com.vivek.expensetrackerapp.domain.models.TransactionCategory
import com.vivek.expensetrackerapp.domain.models.TransactionInfo
import com.vivek.expensetrackerapp.domain.repository.TransactionCategoryRepository
import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import com.vivek.expensetrackerapp.domain.toExternalModel
import javax.inject.Inject


class GetSingleTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val transactionCategoryRepository: TransactionCategoryRepository
){

    suspend operator fun invoke(transactionId:String): TransactionInfo?{
        val transaction = transactionRepository.getTransactionById(transactionId = transactionId)
        val transactionCategory = transaction?.let {
            transactionCategoryRepository.getTransactionCategoryById(
                transactionId = it.transactionCategoryId)
        }
        if (transactionCategory != null) {
            return TransactionInfo(
                transaction = transaction.toExternalModel(),
                category = transactionCategory.toExternalModel(),
            )
        }else{
            return null
        }

    }
}