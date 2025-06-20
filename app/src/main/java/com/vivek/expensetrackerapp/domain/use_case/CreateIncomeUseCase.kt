
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.core.util.Response
import com.vivek.expensetrackerapp.domain.models.Income
import com.vivek.expensetrackerapp.domain.repository.IncomeRepository
import com.vivek.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CreateIncomeUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository,
) {
    suspend operator fun invoke(income: Income): Flow<Resource<Response>> = flow {
        try {
            emit(Resource.Loading())
            incomeRepository.insertIncome(incomeEntity = income.toEntity())
            emit(Resource.Success(data = Response(msg = "Income saved successfully")))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}