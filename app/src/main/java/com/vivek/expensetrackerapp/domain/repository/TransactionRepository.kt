
package com.vivek.expensetrackerapp.domain.repository

import com.vivek.expensetrackerapp.core.room.entities.ExpenseEntity
import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.domain.models.Expense
import com.vivek.expensetrackerapp.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun createTransaction(transaction: Transaction)

    fun getAllTransactions(): Flow<List<TransactionEntity>>

    suspend fun getTransactionById(transactionId:String):TransactionEntity?

    fun getTransactionsForACertainDay(date:String):Flow<List<TransactionEntity>>

    fun getTransactionsBetweenTwoDates(dates:List<String>):Flow<List<TransactionEntity>>

    fun searchTransactions(dates:List<String>,categoryId:String): Flow<List<TransactionEntity>>

    suspend fun deleteTransactionById(transactionId:String)

    fun getTransactionByCategory(categoryId: String): Flow<List<TransactionEntity>>
}