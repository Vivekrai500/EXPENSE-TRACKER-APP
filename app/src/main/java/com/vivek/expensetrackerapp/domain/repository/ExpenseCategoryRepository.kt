
package com.vivek.expensetrackerapp.domain.repository

import com.vivek.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseCategoryRepository {
    suspend fun saveExpenseCategory(expenseCategory: ExpenseCategory)

    suspend fun getExpenseCategoryById(expenseCategoryId: String):ExpenseCategoryEntity?

    fun getAllExpenseCategories(): Flow<List<ExpenseCategoryEntity>>

    suspend fun getExpenseCategoryByName(name:String):ExpenseCategoryEntity?

    suspend fun updateExpenseCategory(expenseCategoryId:String,expenseCategoryName:String)

    suspend fun deleteExpenseCategory(expenseCategoryId:String)

}