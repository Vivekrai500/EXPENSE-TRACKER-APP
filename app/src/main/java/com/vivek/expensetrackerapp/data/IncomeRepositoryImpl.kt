
package com.vivek.expensetrackerapp.data

import com.vivek.expensetrackerapp.core.analytics.analytics.AnalyticsHelper
import com.vivek.expensetrackerapp.core.analytics.analytics.logNewIncome
import com.vivek.expensetrackerapp.core.di.IoDispatcher
import com.vivek.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.vivek.expensetrackerapp.core.room.entities.IncomeEntity
import com.vivek.expensetrackerapp.domain.repository.IncomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val db:ExpenseTrackerAppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val analyticsHelper: AnalyticsHelper,
):IncomeRepository {

    override suspend fun insertIncome(incomeEntity: IncomeEntity) {
        analyticsHelper.logNewIncome()
        withContext(ioDispatcher){
            db.incomeEntityDao.insertIncome(incomeEntity = incomeEntity)
        }
    }

    override  fun getAllIncome(): Flow<List<IncomeEntity>> {
        return db.incomeEntityDao.getAllIncome().flowOn(ioDispatcher)
    }

    override fun getIncomeById(id:String): Flow<IncomeEntity?> {
        return db.incomeEntityDao.getIncomeById(id = id).flowOn(ioDispatcher)
    }

    override suspend fun deleteIncomeById(id: String) {
        withContext(ioDispatcher){
            db.incomeEntityDao.deleteIncomeById(id = id)
        }
    }

    override suspend fun deleteAllIncome() {
        withContext(ioDispatcher){
            db.incomeEntityDao.deleteAllIncome()
        }
    }
}