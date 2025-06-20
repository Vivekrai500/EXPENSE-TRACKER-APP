
package com.vivek.expensetrackerapp.domain

import com.vivek.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.vivek.expensetrackerapp.core.room.entities.ExpenseEntity
import com.vivek.expensetrackerapp.core.room.entities.IncomeEntity
import com.vivek.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.domain.models.Expense
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import com.vivek.expensetrackerapp.domain.models.Income
import com.vivek.expensetrackerapp.domain.models.Transaction
import com.vivek.expensetrackerapp.domain.models.TransactionCategory


fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        transactionId = transactionId,
        transactionName = transactionName,
        transactionAmount = transactionAmount,
        transactionCreatedAt = transactionCreatedAt,
        transactionUpdatedAt = transactionUpdatedAt,
        transactionCategoryId = transactionCategoryId,
        transactionUpdatedOn = transactionUpdatedOn,
        transactionCreatedOn = transactionCreatedOn,
    )
}

fun TransactionEntity.toExternalModel(): Transaction {
    return Transaction(
        transactionId = transactionId,
        transactionName = transactionName,
        transactionAmount = transactionAmount,
        transactionCreatedAt = transactionCreatedAt,
        transactionUpdatedAt = transactionUpdatedAt,
        transactionCategoryId = transactionCategoryId,
        transactionCreatedOn = transactionCreatedOn,
        transactionUpdatedOn = transactionUpdatedOn
    )
}


fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        expenseId = expenseId,
        expenseName = expenseName,
        expenseAmount = expenseAmount,
        expenseCategoryId = expenseCategoryId,
        expenseCreatedAt = expenseCreatedAt,
        expenseUpdatedAt = expenseUpdatedAt,
        expenseUpdatedOn = expenseUpdatedOn,
        expenseCreatedOn = expenseCreatedOn,
    )
}

fun ExpenseEntity.toExternalModel(): Expense {
    return Expense(
        expenseId = expenseId,
        expenseName = expenseName,
        expenseAmount = expenseAmount,
        expenseCategoryId = expenseCategoryId,
        expenseCreatedAt = expenseCreatedAt,
        expenseUpdatedAt = expenseUpdatedAt,
        expenseUpdatedOn = expenseUpdatedOn,
        expenseCreatedOn = expenseCreatedOn,
    )
}


fun TransactionCategory.toEntity(): TransactionCategoryEntity {
    return TransactionCategoryEntity(
        transactionCategoryId = transactionCategoryId,
        transactionCategoryName = transactionCategoryName,


        )
}

fun TransactionCategoryEntity.toExternalModel(): TransactionCategory {
    return TransactionCategory(
        transactionCategoryId = transactionCategoryId,
        transactionCategoryName = transactionCategoryName,


        )
}

fun ExpenseCategory.toEntity(): ExpenseCategoryEntity {
    return ExpenseCategoryEntity(
        expenseCategoryId = expenseCategoryId,
        expenseCategoryName = expenseCategoryName,


        )
}

fun ExpenseCategoryEntity.toExternalModel(): ExpenseCategory {
    return ExpenseCategory(
        expenseCategoryId = expenseCategoryId,
        expenseCategoryName = expenseCategoryName,
        )
}

fun Income.toEntity(): IncomeEntity {
    return IncomeEntity(
        incomeId = incomeId,
        incomeName = incomeName,
        incomeAmount = incomeAmount,
        incomeCreatedAt = incomeCreatedAt,

        )
}

fun IncomeEntity.toExternalModel(): Income {
    return Income(
        incomeId = incomeId,
        incomeName = incomeName,
        incomeAmount = incomeAmount,
        incomeCreatedAt = incomeCreatedAt,
    )
}