
package com.vivek.expensetrackerapp.domain

import com.vivek.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.vivek.expensetrackerapp.core.util.Resource
import com.vivek.expensetrackerapp.domain.models.TransactionCategory
import com.vivek.expensetrackerapp.domain.repository.TransactionCategoryRepository
import com.vivek.expensetrackerapp.domain.use_case.CreateTransactionCategoryUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class CreateTransactionCategoryUseCaseTest {
    // Declare the mock repository object
    private val repository: TransactionCategoryRepository = mockk()

    // Declare the use case object and inject the mock repository
    private val createTransactionCategoryUseCase = CreateTransactionCategoryUseCase(repository)


    @Test
    fun `given non existing transaction category, when use case is invoked, then save transaction category`() =
        runTest {
            // Arrange
            val transactionCategory = TransactionCategory(
                transactionCategoryName = "Test Category",
                transactionCategoryId = UUID.randomUUID().toString(),
            )
            coEvery { repository.getTransactionCategoryByName(any()) } returns null
            coEvery { repository.saveTransactionCategory(any()) } just Runs

            // Act
            val result = createTransactionCategoryUseCase(transactionCategory).toList()

            // Assert
            coVerify(exactly = 1) { repository.saveTransactionCategory(any()) }
            assert(result.last() is Resource.Success)
        }


    @Test
    fun `given existing transaction category, when use case is invoked, then return error`() =
        runTest {
            // Arrange
            val transactionCategory = TransactionCategoryEntity(
                transactionCategoryName = "Test Category",
                transactionCategoryId = UUID.randomUUID().toString(),

            )
            coEvery { repository.getTransactionCategoryByName(any()) } returns transactionCategory

            // Act
            val result =
                createTransactionCategoryUseCase(transactionCategory.toExternalModel()).toList()

            // Assert
            coVerify(exactly = 0) { repository.saveTransactionCategory(any()) }
            assert(result.last() is Resource.Error)

        }

    @Test
    fun `given io exception, when use case is invoked, then return error`() = runTest {
        // Arrange
        val transactionCategory = TransactionCategory(
            transactionCategoryName = "Test Category",
            transactionCategoryId = UUID.randomUUID().toString(),

        )
        val errorMessage = "Test error message"
        coEvery { repository.getTransactionCategoryByName(any()) } throws IOException(errorMessage)

        // Act
        val result = createTransactionCategoryUseCase(transactionCategory).toList()

        // Assert
        coVerify(exactly = 0) { repository.saveTransactionCategory(any()) }
        assert(result.last() is Resource.Error)

    }

}