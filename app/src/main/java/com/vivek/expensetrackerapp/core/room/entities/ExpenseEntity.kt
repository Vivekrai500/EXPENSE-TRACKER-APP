
package com.vivek.expensetrackerapp.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Expenses")
data class ExpenseEntity (
    @PrimaryKey
    val expenseId:String,
    val expenseName:String,
    val expenseAmount:Int,
    val expenseCategoryId:String,

    val expenseCreatedAt: String,
    val expenseCreatedOn:String,
    val expenseUpdatedAt:String,
    val expenseUpdatedOn:String,
)