
package com.vivek.expensetrackerapp.data

import com.vivek.expensetrackerapp.core.analytics.analytics.AnalyticsHelper
import com.vivek.expensetrackerapp.core.analytics.analytics.logNewTransaction
import com.vivek.expensetrackerapp.core.di.IoDispatcher
import com.vivek.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.domain.models.Transaction
import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import com.vivek.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val db: ExpenseTrackerAppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val analyticsHelper: AnalyticsHelper,
) : TransactionRepository {
    override suspend fun createTransaction(transaction: Transaction) {
        analyticsHelper.logNewTransaction()
        return withContext(ioDispatcher) {
            db.transactionEntityDao.insertTransaction(
                transactionEntity = transaction.toEntity()
            )
        }
    }

    override suspend fun getTransactionById(transactionId: String): TransactionEntity? {
        return withContext(ioDispatcher) {
            db.transactionEntityDao.getTransactionById(id = transactionId)
        }
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactions().flowOn(ioDispatcher)

    }

    override fun getTransactionsForACertainDay(date: String): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactionsForACertainDay(date = date)
            .flowOn(ioDispatcher)
    }

    override fun getTransactionsBetweenTwoDates(dates: List<String>): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactionsBetweenTwoDates(dates = dates)
            .flowOn(ioDispatcher)
    }

    override fun searchTransactions(
        dates: List<String>,
        categoryId: String
    ): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.searchTransactions(dates = dates, categoryId = categoryId)
            .flowOn(ioDispatcher)
    }

    override suspend fun deleteTransactionById(transactionId: String) {
        withContext(ioDispatcher){
            db.transactionEntityDao.deleteTransactionById(id = transactionId)
        }
    }

    override fun getTransactionByCategory(categoryId: String): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactionsByCategory(categoryId = categoryId)
            .flowOn(ioDispatcher)
    }

}