
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.domain.models.Income
import com.vivek.expensetrackerapp.domain.repository.IncomeRepository
import com.vivek.expensetrackerapp.domain.toExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllIncomeUseCase @Inject constructor(
    private val repository: IncomeRepository
) {

    operator fun invoke(): Flow<List<Income>> {
        return repository.getAllIncome().map { it.map { it.toExternalModel() } }
    }
}