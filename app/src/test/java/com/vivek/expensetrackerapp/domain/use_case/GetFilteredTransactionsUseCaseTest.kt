
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.core.util.FilterConstants
import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetFilteredTransactionsUseCaseTest {
    private val repository: TransactionRepository = mockk()

    private val getFilteredTransactionUseCase = GetFilteredTransactionsUseCase(repository)

    val mockedTransactions: Flow<List<TransactionEntity>> = mockk()

    @Test
    fun `When passed today date is passed ,assert that getTransactionsForACertainDay runs `() =
        runTest {
            coEvery { repository.getTransactionsForACertainDay(any()) } returns mockedTransactions
            coEvery { repository.getTransactionsBetweenTwoDates(any()) } returns mockedTransactions

            val result = getFilteredTransactionUseCase(FilterConstants.TODAY)
            coVerify(atLeast = 1, atMost = 1) {
                repository.getTransactionsForACertainDay(any())
            }


        }

    @Test
    fun `When passed this week, this month, last 7 days getTransactionsBetweenTwoDates runs`() =
        runTest {
            coEvery { repository.getTransactionsForACertainDay(any()) } returns mockedTransactions
            coEvery { repository.getTransactionsBetweenTwoDates(any()) } returns mockedTransactions
            val result = getFilteredTransactionUseCase(FilterConstants.THIS_WEEK)

            coVerify(atLeast = 1) {
                repository.getTransactionsBetweenTwoDates(any())
            }
        }
}