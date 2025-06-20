
package com.vivek.expensetrackerapp.presentation.screens.analytics.transactions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.charts.bar.BarChartData

import com.vivek.expensetrackerapp.core.room.entities.TransactionEntity
import com.vivek.expensetrackerapp.core.util.*
import com.vivek.expensetrackerapp.domain.models.Transaction
import com.vivek.expensetrackerapp.domain.use_case.GetFilteredTransactionsUseCase
import com.vivek.expensetrackerapp.domain.use_case.GetTransactionsForACertainDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class GraphItem(
    val total:Int,
    val transactions:List<Transaction>
)
@HiltViewModel
class AnalyticsScreenViewModel @Inject constructor(
    private val getTransactionsForACertainDayUseCase: GetTransactionsForACertainDayUseCase,
    private val getFilteredTransactionsUseCase: GetFilteredTransactionsUseCase,


) : ViewModel(){
    val todayDate = generateFormatDate(LocalDate.now())
    val weekDates = getWeekDates(dateString = todayDate)
    val sevendaysBefore = generate7daysPriorDate(todayDate)
    val sevenDaysBeforeDates = datesBetween(sevendaysBefore,todayDate)
    val monthDates = getMonthDates(dateString = todayDate)



    val _graphData = mutableStateOf<List<Flow<List<TransactionEntity>>>>(emptyList())
    val graphData : State<List<Flow<List<TransactionEntity>>>> = _graphData

    val _barDataList = mutableStateOf<List<BarChartData.Bar>>(emptyList())
    val barDataList:State<List<BarChartData.Bar>> = _barDataList

    val _activeFilterConstant = mutableStateOf(FilterConstants.THIS_WEEK)
    val activeFilterConstant:State<String> = _activeFilterConstant


    fun onChangeBarDataList(data:List<BarChartData.Bar>){
        _barDataList.value = data
    }

    fun onChangeActiveFilterConstant(filter:String){
        _activeFilterConstant.value = filter
        if (filter == FilterConstants.LAST_7_DAYS){
            getGraphData(dates = sevenDaysBeforeDates)
        }else if (filter == FilterConstants.THIS_MONTH){
            getGraphData(dates = monthDates)
        }else if (filter == FilterConstants.THIS_WEEK){
            getGraphData(dates = weekDates)
        }else if (filter == FilterConstants.ALL){
            getAllTransactions()
        }
    }

    private fun getAllTransactions(){
        viewModelScope.launch {
            val data = getFilteredTransactionsUseCase(filter = FilterConstants.ALL)
            _graphData.value = listOf(data)
        }
    }


    init {
        getGraphData(dates = weekDates)
    }

    private fun getGraphData(dates:List<String>){
        viewModelScope.launch {
            val data = dates.map {
                getTransactionsForACertainDayUseCase(date = it)
            }
            _graphData.value = data
        }
    }







}