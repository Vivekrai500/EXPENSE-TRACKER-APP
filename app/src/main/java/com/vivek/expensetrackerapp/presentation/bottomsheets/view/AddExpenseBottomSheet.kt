
package com.vivek.expensetrackerapp.presentation.bottomsheets.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vivek.expensetrackerapp.core.util.UiEvent
import com.vivek.expensetrackerapp.core.util.getNumericInitialValue
import com.vivek.expensetrackerapp.core.util.toast
import com.vivek.expensetrackerapp.domain.models.ExpenseCategory
import com.vivek.expensetrackerapp.domain.toExternalModel
import com.vivek.expensetrackerapp.presentation.bottomsheets.viewModels.AddExpenseFormState
import com.vivek.expensetrackerapp.presentation.bottomsheets.viewModels.AddExpenseScreenViewModel
import com.vivek.expensetrackerapp.presentation.components.MenuSample
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddExpenseBottomSheet(
    viewModel: AddExpenseScreenViewModel = hiltViewModel()
) {
    val expenseCategories = viewModel.expenseCategories
        .collectAsStateWithLifecycle()
        .value
        .map { it.toExternalModel() }

    val formState = viewModel.formState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {
            when(it){
                is UiEvent.ShowSnackbar -> {
                    context.toast(msg = it.uiText)
                }
                else -> {}
            }
        }
    }

    AddExpenseBottomSheetContent(
        expenseCategories = expenseCategories,
        formState = formState.value,
        onChangeExpenseName = {
            viewModel.onChangeExpenseName(it)
        },
        onChangeExpenseAmount = {
            viewModel.onChangeExpenseAmount(it)
        },
        onChangeExpenseCategory = {
            viewModel.onChangeSelectedExpenseCategory(it)
        },
        addExpense = {
            viewModel.addExpense()
        }
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddExpenseBottomSheetContent(
    expenseCategories: List<ExpenseCategory>,
    formState: AddExpenseFormState,
    onChangeExpenseName: (String) -> Unit,
    onChangeExpenseAmount: (String) -> Unit,
    onChangeExpenseCategory: (ExpenseCategory) -> Unit,
    addExpense: () -> Unit,

    ) {
    val keyBoard = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(370.dp)
    ) {
        if (formState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onBackground)
                .fillMaxSize()
                .padding(10.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = "Create Expense ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            OutlinedTextField(
                value = formState.expenseName,
                onValueChange = {
                    onChangeExpenseName(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Expense Name"
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )

            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,

                ) {
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = getNumericInitialValue(formState.expenseAmount),
                    onValueChange = {
                        onChangeExpenseAmount(it)
                    },

                    modifier = Modifier.fillMaxWidth(0.5f),
                    placeholder = {
                        Text(
                            text = "Expense Amount",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                val currentIndex =
                    if (formState.selectedExpenseCategory == null) 0
                else
                    expenseCategories.map { it.expenseCategoryName }.indexOf(formState.selectedExpenseCategory.expenseCategoryName)
                MenuSample(
                    menuWidth = 300,
                    selectedIndex = currentIndex,
                    menuItems = expenseCategories.map { it.expenseCategoryName },
                    onChangeSelectedIndex = {
                        val selectedExpenseCategory = expenseCategories[it]
                        onChangeExpenseCategory(selectedExpenseCategory)

                    }
                )
            }
            if (expenseCategories.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Enter an expense category to be add to an expense",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            Button(
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    keyBoard?.hide()
                    addExpense()
                }
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

            }


        }
    }

}