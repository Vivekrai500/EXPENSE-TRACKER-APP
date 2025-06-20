
package com.vivek.expensetrackerapp.presentation.screens.analytics.expenses

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.vivek.expensetrackerapp.core.util.FilterConstants
import com.vivek.expensetrackerapp.core.util.generateRandomColor
import com.vivek.expensetrackerapp.domain.models.Expense
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import com.vivek.expensetrackerapp.domain.toExternalModel
import com.vivek.expensetrackerapp.presentation.components.ExpenseCard
import com.vivek.expensetrackerapp.presentation.components.GraphFilterCard

data class PieDataInfo(
    val slice: PieChartData.Slice,
    val expenseCategory: ExpenseCategory,
)

@Composable
fun ExpensesAnalyticsScreen(
    viewModel: ExpenseAnalyticsScreenViewModel = hiltViewModel(),
    navigateToExpenseScreen: (String) -> Unit,
) {

    val expensesCategoriesInfo = viewModel.expenseCategories.value
    val pieData = expensesCategoriesInfo.map { expenseCategoryInfoEntity ->
        val total = expenseCategoryInfoEntity.expenses
            .collectAsStateWithLifecycle(initialValue = emptyList())
            .value
            .map { it.toExternalModel() }
            .map { it.expenseAmount.toFloat() }
            .sum()
        PieDataInfo(
            slice = PieChartData.Slice(
                value = total,
                color = generateRandomColor()
            ),
            expenseCategory = expenseCategoryInfoEntity.expenseCategory
        )
    }


    val expenses = expensesCategoriesInfo.map { it.expenses }
        .map { it.collectAsStateWithLifecycle(initialValue = emptyList()) }
        .map { it.value }
        .flatten()
        .map { it.toExternalModel() }


    ExpensesAnalyticsScreenContent(
        pieData = pieData,
        navigateToExpenseScreen = navigateToExpenseScreen,
        expenseCategories = expensesCategoriesInfo.map { it.expenseCategory },
        expenses = expenses
    )
}


@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExpensesAnalyticsScreenContent(
    pieData: List<PieDataInfo>,
    navigateToExpenseScreen: (String) -> Unit,
    expenseCategories: List<ExpenseCategory>,
    expenses:List<Expense>,

    ) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    PieChart(
                        pieChartData = PieChartData(slices = pieData.map { it.slice }),
                        modifier = Modifier
                            .size(210.dp)
                            .padding(10.dp)
                            ,
                        animation = simpleChartAnimation(),
                        sliceDrawer = SimpleSliceDrawer(),
                    )

                }
            }
            items(items = pieData){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Box(
                        modifier = Modifier
                            .width(30.dp)
                            .height(20.dp)
                            .background(color = it.slice.color)
                            .padding(3.dp)
                    )
                    Text(
                        text = it.expenseCategory.expenseCategoryName,
                        modifier = Modifier.padding(3.dp),
                        fontSize = 17.sp,
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = "Rs " + it.slice.value.toString() + "/=",
                        modifier = Modifier.padding(3.dp),
                        fontSize = 17.sp,
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

    }
}