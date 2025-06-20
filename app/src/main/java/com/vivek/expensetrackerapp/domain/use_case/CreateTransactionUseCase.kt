
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.core.util.Response
import com.vivek.expensetrackerapp.domain.models.Expense
import com.vivek.expensetrackerapp.domain.models.Transaction
import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction): Flow<Resource<Response>> = flow {
        try {
            emit(Resource.Loading())
            repository.createTransaction(transaction = transaction)
            emit(Resource.Success(data = Response(msg = "Transaction saved successfully")))
        }catch (e:IOException){
            emit(Resource.Error(message = e.localizedMessage?: "An unexpected error occurred"))
        }
    }
}