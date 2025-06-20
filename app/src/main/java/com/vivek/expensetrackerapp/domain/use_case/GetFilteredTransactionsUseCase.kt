
package com.vivek.expensetrackerapp.domain.use_case

import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.core.util.*
import com.vivek.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.util.Locale.filter
import javax.inject.Inject


class GetFilteredTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    val todayDate = generateFormatDate(date = LocalDate.now())
    val yesterdayDate = previousDay(date = todayDate)
    val weekDates = getWeekDates(dateString = todayDate)
    val monthDates = getMonthDates(dateString = todayDate)
    val oneWeekEarlierDate = generate7daysPriorDate(date = todayDate)
    val daysInThe7daysPrior = datesBetween(startDate = oneWeekEarlierDate, endDate = todayDate)

    operator fun invoke(filter: String): Flow<List<TransactionEntity>> {
        when (filter) {
            FilterConstants.TODAY -> {
                return repository.getTransactionsForACertainDay(date = todayDate)
            }
            FilterConstants.YESTERDAY -> {
                return repository.getTransactionsForACertainDay(date = yesterdayDate)
            }
            FilterConstants.THIS_WEEK -> {
                return repository.getTransactionsBetweenTwoDates(dates = weekDates)
            }
            FilterConstants.LAST_7_DAYS -> {
                return repository.getTransactionsBetweenTwoDates(dates = daysInThe7daysPrior)
            }
            FilterConstants.THIS_MONTH -> {
                return repository.getTransactionsBetweenTwoDates(dates = monthDates)
            }
            FilterConstants.ALL -> {
                return repository.getAllTransactions()
            }
            else ->  {
                return flow { emptyList<TransactionEntity>() }
            }
        }

    }
}