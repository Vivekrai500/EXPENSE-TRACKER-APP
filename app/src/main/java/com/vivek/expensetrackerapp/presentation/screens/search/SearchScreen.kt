
package com.vivek.expensetrackerapp.presentation.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vivek.expensetrackerapp.core.util.Screens
import com.vivek.expensetrackerapp.core.util.UiEvent
import com.vivek.expensetrackerapp.core.util.convertTimeMillisToLocalDate
import com.vivek.expensetrackerapp.core.util.getFormattedDate
import com.vivek.expensetrackerapp.domain.models.TransactionCategory
import com.vivek.expensetrackerapp.domain.toExternalModel
import com.vivek.expensetrackerapp.presentation.components.CustomIconButton
import com.vivek.expensetrackerapp.presentation.components.MenuSample
import com.vivek.expensetrackerapp.presentation.components.TransactionCard
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import com.vivek.expensetrackerapp.R

@Composable
fun SearchScreen(
    navigateToTransactionScreen: (String) -> Unit,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val transactionCategories = viewModel.transactionCategories
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
        .map { it.toExternalModel() }


    SearchScreenContent(
        uiState = uiState.value,
        transactionCategories = transactionCategories,
        searchTransactions = viewModel::searchTransactions,
        onChangeTransactionCategory = viewModel::onChangeTransactionCategory,
        onChangeTransactionStartDate = viewModel::onChangeTransactionStartDate,
        onChangeTransactionEndDate = viewModel::onChangeTransactionEndDate,
        navigateToTransactionScreen = navigateToTransactionScreen,
        eventFlow = viewModel.eventFlow
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreenContent(
    navigateToTransactionScreen: (String) -> Unit,
    uiState: SearchScreenState,
    transactionCategories: List<TransactionCategory>,
    searchTransactions: () -> Unit,
    onChangeTransactionCategory: (TransactionCategory) -> Unit,
    onChangeTransactionStartDate: (LocalDate) -> Unit,
    onChangeTransactionEndDate: (LocalDate) -> Unit,
    eventFlow: SharedFlow<UiEvent>


) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val state = rememberDateRangePickerState()

    LaunchedEffect(key1 = state.selectedEndDateMillis, key2 = state.selectedStartDateMillis) {
        state.selectedStartDateMillis?.let {
            onChangeTransactionStartDate(convertTimeMillisToLocalDate(it))
        }
        state.selectedEndDateMillis?.let {
            onChangeTransactionEndDate(convertTimeMillisToLocalDate(it))
        }
    }

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.uiText
                    )
                }

                is UiEvent.Navigate -> {

                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.search),
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    scrolledContainerColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            searchTransactions()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search Transactions",
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(3.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = "Start Date : " + uiState.startDate.toString(),
                            style = TextStyle(color = MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            text = "End Date : " + uiState.endDate.toString(),
                            style = TextStyle(color = MaterialTheme.colorScheme.primary)
                        )
                        Button(
                            colors = ButtonColors(
                                contentColor = MaterialTheme.colorScheme.background,
                                containerColor = MaterialTheme.colorScheme.onBackground,
                                disabledContainerColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            onClick = {
                                showBottomSheet.value = true
                            }) {
                            Text(
                                text = "Open Date Range Picker",
                                style = TextStyle(color = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 5.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = "Category",
                            modifier = Modifier.fillMaxWidth(0.5f),
                            style = TextStyle(color = MaterialTheme.colorScheme.primary)
                        )
                        val currentIndex =
                            if (uiState.transactionCategory == null) 0
                            else
                                transactionCategories
                                    .map { it.transactionCategoryName }
                                    .indexOf(uiState.transactionCategory.transactionCategoryName)
                        MenuSample(
                            menuWidth = 190,
                            selectedIndex = currentIndex,
                            menuItems = transactionCategories.map { transactionCategory -> transactionCategory.transactionCategoryName },
                            onChangeSelectedIndex = {

                                val selectedTransactionCategory = transactionCategories[it]
                                onChangeTransactionCategory(selectedTransactionCategory)

                            }
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.transactions.isEmpty()) {
                        Text(
                            text = "No transactions found ",
                            style = TextStyle(color = MaterialTheme.colorScheme.primary),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (uiState.transactions.isNotEmpty()) {
                        Text(
                            text = "A total of ${uiState.transactions.size} transactions have been made " +
                                    "between ${uiState.startDate}" +
                                    " and ${uiState.endDate}",
                            style = TextStyle(color = MaterialTheme.colorScheme.primary),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            items(items = uiState.transactions) { transaction ->
                TransactionCard(
                    transaction = transaction,
                    onTransactionNavigate = {
                        navigateToTransactionScreen(it)
                    }
                )
            }
        }
        if (showBottomSheet.value) {
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.onBackground,
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
                DateRangePicker(
                    state = state,
                    modifier = Modifier,
                    title = {
                        Text(
                            text = "Select date range to assign the chart",
                            style = TextStyle(color = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    headline = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                (if (state.selectedStartDateMillis != null) state.selectedStartDateMillis?.let {
                                    getFormattedDate(it)
                                } else "Start Date")?.let {
                                    Text(
                                        text = it,
                                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                                    )
                                }
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                (if (state.selectedEndDateMillis != null) state.selectedEndDateMillis?.let {
                                    getFormattedDate(it)
                                } else "End Date")?.let {
                                    Text(
                                        text = it,
                                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                                    )
                                }
                            }
                            Box(modifier = Modifier.weight(0.2f)) {
                                CustomIconButton(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Done ",
                                    onClick = {
                                        showBottomSheet.value = false
                                    }
                                )

                            }

                        }
                    },
                    showModeToggle = true,
                    colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        headlineContentColor = MaterialTheme.colorScheme.primary,
                        weekdayContentColor = MaterialTheme.colorScheme.primary,
                        subheadContentColor = MaterialTheme.colorScheme.primary,
                        yearContentColor = MaterialTheme.colorScheme.primary,
                        currentYearContentColor = MaterialTheme.colorScheme.primary,
                        selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                        disabledDayContentColor = Color.Gray,
                        todayDateBorderColor = MaterialTheme.colorScheme.primary,
                        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.background,
                        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.primary,
                        selectedDayContainerColor = MaterialTheme.colorScheme.background
                    )
                )
            }

        }
    }
}


