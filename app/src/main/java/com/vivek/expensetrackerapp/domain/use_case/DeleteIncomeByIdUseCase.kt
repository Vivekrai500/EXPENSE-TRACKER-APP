
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.domain.repository.IncomeRepository
import javax.inject.Inject

class DeleteIncomeByIdUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(incomeId:String){
        return repository.deleteIncomeById(id = incomeId)
    }
}