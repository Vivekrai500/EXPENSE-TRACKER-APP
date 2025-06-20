
package com.vivek.expensetrackerapp.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.vivek.expensetrackerapp.core.room.dao.ExpenseCategoryEntityDao
import com.vivek.expensetrackerapp.core.room.dao.ExpenseEntityDao
import com.vivek.expensetrackerapp.core.room.dao.IncomeEntityDao
import com.vivek.expensetrackerapp.core.room.dao.TransactionCategoryEntityDao
import com.vivek.expensetrackerapp.core.room.dao.TransactionEntityDao
import com.vivek.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.vivek.expensetrackerapp.core.room.entities.ExpenseEntity
import com.vivek.expensetrackerapp.core.room.entities.IncomeEntity
import com.vivek.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.core.room.converters.DateConverter

@TypeConverters(DateConverter::class)
@Database(
    entities = [
        ExpenseCategoryEntity::class,
        TransactionCategoryEntity::class,
        ExpenseEntity::class,
        TransactionEntity::class,
        IncomeEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class ExpenseTrackerAppDatabase : RoomDatabase() {


    abstract val expenseCategoryEntityDao: ExpenseCategoryEntityDao

    abstract val transactionCategoryEntityDao: TransactionCategoryEntityDao

    abstract val expenseEntityDao:ExpenseEntityDao

    abstract val transactionEntityDao:TransactionEntityDao

    abstract val incomeEntityDao:IncomeEntityDao

}