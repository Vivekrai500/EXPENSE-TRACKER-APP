
package com.vivek.expensetrackerapp.data

import com.vivek.expensetrackerapp.core.analytics.analytics.AnalyticsHelper
import com.vivek.expensetrackerapp.core.analytics.analytics.logNewExpense
import com.vivek.expensetrackerapp.core.di.IoDispatcher
import com.vivek.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.vivek.expensetrackerapp.core.room.entities.ExpenseEntity
import com.vivek.expensetrackerapp.domain.models.Expense
import com.vivek.expensetrackerapp.domain.repository.ExpenseRepository
import com.vivek.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val db:ExpenseTrackerAppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val analyticsHelper: AnalyticsHelper,
): ExpenseRepository {
    override suspend fun createExpense(expense: Expense) {
        analyticsHelper.logNewExpense()
        withContext(ioDispatcher){
            db.expenseEntityDao.insertExpense(expenseEntity = expense.toEntity())
        }
    }

    override suspend fun getExpenseById(expenseId: String): ExpenseEntity? {
        return withContext(ioDispatcher){
            db.expenseEntityDao.getExpenseById(expenseId)
        }
    }

    override fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return db.expenseEntityDao.getAllExpenses().flowOn(ioDispatcher)
    }

    override suspend fun deleteExpenseById(expenseId: String) {
        withContext(ioDispatcher){
            db.expenseEntityDao.deleteExpenseById(id = expenseId)
        }
    }
    override fun getExpensesByCategory(categoryId: String): Flow<List<ExpenseEntity>> {
        return db.expenseEntityDao.getExpensesByCategory(id = categoryId).flowOn(ioDispatcher)
    }

    override suspend fun updateExpense(
        expenseName: String,
        expenseAmount: Int,
        expenseUpdatedAt: String,
        expenseUpdatedOn: String
    ) {
        TODO("Not yet implemented")
    }


}