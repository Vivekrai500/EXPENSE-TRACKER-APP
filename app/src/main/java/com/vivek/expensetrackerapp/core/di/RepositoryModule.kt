
package com.vivek.expensetrackerapp.core.di

import com.vivek.expensetrackerapp.core.analytics.analytics.AnalyticsHelper
import com.vivek.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.vivek.expensetrackerapp.data.ExpenseCategoryRepositoryImpl
import com.vivek.expensetrackerapp.data.ExpenseRepositoryImpl
import com.vivek.expensetrackerapp.data.IncomeRepositoryImpl
import com.vivek.expensetrackerapp.data.TransactionCategoryRepositoryImpl
import com.vivek.expensetrackerapp.data.TransactionRepositoryImpl
import com.vivek.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import com.vivek.expensetrackerapp.domain.repository.ExpenseRepository
import com.vivek.expensetrackerapp.domain.repository.IncomeRepository
import com.vivek.expensetrackerapp.domain.repository.TransactionCategoryRepository
import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExpenseCategoryRepository(
        database: ExpenseTrackerAppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        analyticsHelper: AnalyticsHelper,
    ): ExpenseCategoryRepository {
        return ExpenseCategoryRepositoryImpl(
            db = database,
            ioDispatcher = ioDispatcher,
            analyticsHelper = analyticsHelper
        )
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(
        database: ExpenseTrackerAppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        analyticsHelper: AnalyticsHelper,
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(
            db = database,
            ioDispatcher = ioDispatcher,
            analyticsHelper = analyticsHelper
        )
    }

    @Provides
    @Singleton
    fun provideTransactionCategoryRepository(
        database: ExpenseTrackerAppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        analyticsHelper: AnalyticsHelper,
    ): TransactionCategoryRepository {
        return TransactionCategoryRepositoryImpl(
            db = database,
            ioDispatcher = ioDispatcher,
            analyticsHelper = analyticsHelper
        )
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        database: ExpenseTrackerAppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        analyticsHelper: AnalyticsHelper,
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            db = database,
            ioDispatcher = ioDispatcher,
            analyticsHelper = analyticsHelper
        )
    }

    @Provides
    @Singleton
    fun provideIncomeRepository(
        database: ExpenseTrackerAppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        analyticsHelper: AnalyticsHelper,
    ): IncomeRepository {
        return IncomeRepositoryImpl(
            db = database,
            ioDispatcher = ioDispatcher,
            analyticsHelper = analyticsHelper
        )
    }
}