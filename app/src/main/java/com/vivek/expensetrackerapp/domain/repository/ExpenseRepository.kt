
package com.vivek.expensetrackerapp.domain.repository

import com.vivek.expensetrackerapp.core.room.entities.ExpenseEntity
import com.vivek.expensetrackerapp.domain.models.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    suspend fun createExpense(expense: Expense)

    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    suspend fun getExpenseById(expenseId:String):ExpenseEntity?

    suspend fun deleteExpenseById(expenseId:String)

    suspend fun updateExpense(
        expenseName:String,
        expenseAmount:Int,
        expenseUpdatedAt:String,
        expenseUpdatedOn:String,
    )

    fun getExpensesByCategory(categoryId:String): Flow<List<ExpenseEntity>>

}