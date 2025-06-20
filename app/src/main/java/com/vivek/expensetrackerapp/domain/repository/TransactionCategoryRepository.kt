
package com.vivek.expensetrackerapp.domain.repository

import com.vivek.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.vivek.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import com.vivek.expensetrackerapp.domain.models.TransactionCategory
import kotlinx.coroutines.flow.Flow

interface TransactionCategoryRepository {

    suspend fun saveTransactionCategory(transactionCategory: TransactionCategory)

    fun getAllTransactionCategories(): Flow<List<TransactionCategoryEntity>>

    suspend fun getTransactionCategoryById(transactionId:String):TransactionCategoryEntity?

    suspend fun getTransactionCategoryByName(name:String): TransactionCategoryEntity?

    suspend fun updateTransactionCategory(transactionCategoryId:String,transactionCategoryName:String)

    suspend fun deleteTransactionCategory(transactionCategoryId:String)


}