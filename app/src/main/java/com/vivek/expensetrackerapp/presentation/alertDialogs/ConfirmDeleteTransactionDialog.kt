package com.vivek.expensetrackerapp.presentation.alertDialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.vivek.expensetrackerapp.R
import com.vivek.expensetrackerapp.domain.models.Transaction

@Composable
fun ConfirmDeleteTransactionDialog(
    closeDeleteDialog:() -> Unit,
    transaction: Transaction,
    deleteTransaction:() -> Unit,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = {
            closeDeleteDialog()
        },
        title = {
            Text(
                text = "${stringResource(id = R.string.delete)} '${transaction.transactionName}' ",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                )
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.delete_expense_description),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    deleteTransaction()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closeDeleteDialog()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        }
    )


}