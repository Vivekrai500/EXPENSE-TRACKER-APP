
package com.vivek.expensetrackerapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.annotation.ExperimentalCoilApi
import com.vivek.expensetrackerapp.R
import com.vivek.expensetrackerapp.core.util.Screens
import com.vivek.expensetrackerapp.core.util.TestTags
import com.vivek.expensetrackerapp.core.util.UiEvent
import com.vivek.expensetrackerapp.presentation.bottomsheets.view.AddExpenseBottomSheet
import com.vivek.expensetrackerapp.presentation.bottomsheets.view.AddExpenseCategoryBottomSheet
import com.vivek.expensetrackerapp.presentation.bottomsheets.view.AddIncomeBottomSheet
import com.vivek.expensetrackerapp.presentation.bottomsheets.view.AddTransactionBottomSheet
import com.vivek.expensetrackerapp.presentation.bottomsheets.view.AddTransactionCategoryBottomSheet
import com.vivek.expensetrackerapp.presentation.bottomsheets.view.IncomeInfoBottomSheet
import com.vivek.expensetrackerapp.presentation.components.EmptyComponent
import com.vivek.expensetrackerapp.presentation.components.ErrorComponent
import com.vivek.expensetrackerapp.presentation.components.ExpenseCard
import com.vivek.expensetrackerapp.presentation.components.HomeScreenActionsCard
import com.vivek.expensetrackerapp.presentation.components.HomeSubHeader
import com.vivek.expensetrackerapp.presentation.components.IncomeCard
import com.vivek.expensetrackerapp.presentation.components.LoadingComponent
import com.vivek.expensetrackerapp.presentation.components.TransactionCard
import com.vivek.expensetrackerapp.presentation.theme.GreyColor
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navigateToAllIncomeScreen: () -> Unit,
    navigateToAllTransactionsScreen: () -> Unit,
    navigateToAllExpensesScreen: () -> Unit,
    navigateToTransactionScreen: (String) -> Unit,
    navigateToExpenseScreen:(String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    HomeScreenContent(
        uiState = uiState,
        eventFlow = viewModel.eventFlow,
        activeBottomSheet = viewModel.activeBottomSheet.value,
        onChangeActiveBottomSheet = { viewModel.onChangeActiveBottomSheet(it) },
        activeIncomeId = viewModel.activeIncomeId.value,
        navigateToAllIncomeScreen = navigateToAllIncomeScreen,
        navigateToAllTransactionsScreen = navigateToAllTransactionsScreen,
        navigateToAllExpensesScreen = navigateToAllExpensesScreen,
        navigateToTransactionScreen = navigateToTransactionScreen,
        navigateToExpenseScreen = navigateToExpenseScreen,
        onChangeActiveIncomeId = {
            viewModel.onChangeActiveIncomeId(it)
        },
    )

}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    eventFlow: SharedFlow<UiEvent>,
    activeBottomSheet: BottomSheets?,
    onChangeActiveBottomSheet: (BottomSheets) -> Unit,
    activeIncomeId: String?,
    onChangeActiveIncomeId: (String) -> Unit,
    navigateToAllIncomeScreen: () -> Unit,
    navigateToAllTransactionsScreen: () -> Unit,
    navigateToAllExpensesScreen: () -> Unit,
    navigateToTransactionScreen: (String) -> Unit,
    navigateToExpenseScreen:(String) -> Unit,

    ) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {

                }

                is UiEvent.Navigate -> {

                }

                is UiEvent.OpenBottomSheet -> {
                    showBottomSheet = true
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(tag = Screens.HOME_SCREEN),
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag(TestTags.HOME_SCREEN_TOP_APP_BAR_TAG),
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    scrolledContainerColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
            )
        },
    ) { paddingValues ->
        when (uiState) {
            is HomeScreenUiState.Loading -> {
                LoadingComponent()
            }

            is HomeScreenUiState.Error -> {
                ErrorComponent(message = uiState.message)
            }

            is HomeScreenUiState.Success -> {
                val totalIncome = uiState.income.sumOf { it.incomeAmount }
                val totalExpense = uiState.expenses.sumOf { it.expenseAmount }
                val totalTransaction = uiState.transactions.sumOf { it.transactionAmount }

                val remainingIncome = totalIncome - (totalExpense + totalTransaction)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(10.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .height(100.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Your Income ",
                                    style = TextStyle(color = GreyColor),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Rs $remainingIncome /=",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp,
                                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                                )

                            }
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("home:menuOptions")
                                ,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                HomeScreenActionsCard(
                                    name = "Add Income",
                                    icon = Icons.Outlined.Payments,
                                    onClick = {
                                        onChangeActiveBottomSheet(
                                            BottomSheets.ADD_INCOME
                                        )
                                    }
                                )
                                HomeScreenActionsCard(
                                    name = "Add Transaction",
                                    icon = Icons.AutoMirrored.Outlined.ReceiptLong,
                                    onClick = {
                                        onChangeActiveBottomSheet(
                                            BottomSheets.ADD_TRANSACTION
                                        )
                                    }
                                )
                                HomeScreenActionsCard(
                                    name = "Add Expense",
                                    icon = Icons.Outlined.ShoppingCart,
                                    onClick = {
                                        onChangeActiveBottomSheet(
                                            BottomSheets.ADD_EXPENSE
                                        )
                                    }
                                )
                                HomeScreenActionsCard(
                                    name = "Add Transaction Category",
                                    icon = Icons.Default.Add,
                                    onClick = {
                                        onChangeActiveBottomSheet(
                                            BottomSheets.ADD_TRANSACTION_CATEGORY
                                        )
                                    }
                                )
                                HomeScreenActionsCard(
                                    name = "Add Expense Category",
                                    icon = Icons.Default.Add,
                                    onClick = {
                                        onChangeActiveBottomSheet(
                                            BottomSheets.ADD_EXPENSE_CATEGORY
                                        )
                                    }
                                )

                            }
                        }
                        item {
                            HomeSubHeader(
                                name = "Income",
                                onClick = {
                                    navigateToAllIncomeScreen()
                                }
                            )
                        }
                        if (uiState.income.isEmpty()) {
                            item {
                                EmptyComponent(message = "No income saved")
                            }
                        } else {
                            items(
                                items = uiState.income.take(n = 2),
                                key = { it.incomeId }) { income ->
                                IncomeCard(
                                    income = income,
                                    onIncomeNavigate = {
                                        onChangeActiveIncomeId(it)
                                        onChangeActiveBottomSheet(BottomSheets.VIEW_INCOME)
                                    }
                                )
                            }
                        }

                        item {
                            HomeSubHeader(
                                name = "Transactions",
                                onClick = {
                                    navigateToAllTransactionsScreen()
                                }
                            )
                        }
                        if (uiState.transactions.isEmpty()) {
                            item {
                                EmptyComponent(message = "No transactions saved")
                            }
                        } else {
                            items(
                                items = uiState.transactions.take(n = 2),
                                key = { it.transactionId }) { transaction ->
                                TransactionCard(
                                    transaction = transaction,
                                    onTransactionNavigate = {
                                        navigateToTransactionScreen(it)
                                    }
                                )
                            }
                        }
                        item {
                            HomeSubHeader(
                                name = "Expenses",
                                onClick = {
                                    navigateToAllExpensesScreen()
                                }
                            )
                        }
                        if (uiState.expenses.isEmpty()) {
                            item {
                                EmptyComponent(message = "No expenses saved")
                            }
                        } else {
                            items(
                                items = uiState.expenses.take(n = 2),
                                key = { it.expenseId }) { expense ->
                                ExpenseCard(
                                    expense = expense,
                                    onExpenseNavigate = { expenseId ->
                                        navigateToExpenseScreen(expenseId)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.onBackground,
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                when (activeBottomSheet) {
                    BottomSheets.ADD_EXPENSE -> {
                        AddExpenseBottomSheet()
                    }

                    BottomSheets.ADD_EXPENSE_CATEGORY -> {
                        AddExpenseCategoryBottomSheet()
                    }

                    BottomSheets.ADD_TRANSACTION -> {
                        AddTransactionBottomSheet()
                    }

                    BottomSheets.ADD_TRANSACTION_CATEGORY -> {
                        AddTransactionCategoryBottomSheet()
                    }

                    BottomSheets.ADD_INCOME -> {
                        AddIncomeBottomSheet()
                    }

                    BottomSheets.VIEW_INCOME -> {
                        IncomeInfoBottomSheet(
                            activeIncomeId = activeIncomeId
                        )

                    }

                    else -> {}
                }
            }
        }
    }
}



