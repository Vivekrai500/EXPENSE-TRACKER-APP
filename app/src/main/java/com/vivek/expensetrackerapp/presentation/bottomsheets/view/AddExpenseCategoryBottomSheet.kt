
package com.vivek.expensetrackerapp.presentation.bottomsheets.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vivek.expensetrackerapp.core.util.UiEvent
import com.vivek.expensetrackerapp.core.util.toast
import com.vivek.expensetrackerapp.presentation.bottomsheets.viewModels.AddExpenseCategoryFormState
import com.vivek.expensetrackerapp.presentation.bottomsheets.viewModels.AddExpenseCategoryScreenViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddExpenseCategoryBottomSheet(
    viewModel: AddExpenseCategoryScreenViewModel = hiltViewModel()
) {
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
    AddExpenseCategoryBottomSheetContent(
        formState = formState.value,
        onChangeExpenseCategoryName = {
            viewModel.onChangeExpenseName(it)
        },
        addExpenseCategory = { viewModel.addExpenseCategory() },

    )

}


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddExpenseCategoryBottomSheetContent(
    formState: AddExpenseCategoryFormState,
    onChangeExpenseCategoryName: (String) -> Unit,
    addExpenseCategory: () -> Unit,
) {
    val keyBoard = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onBackground)
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
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
                    text = "Create Expense Category",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            OutlinedTextField(
                value = formState.expenseCategoryName,
                onValueChange = {
                    onChangeExpenseCategoryName(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Expense Category Name",
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = {
                    keyBoard?.hide()
                    addExpenseCategory()

                }
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),

                    )

            }
        }
        if (formState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}