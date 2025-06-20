
package com.vivek.expensetrackerapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.vivek.expensetrackerapp.core.util.generateAvatarURL
import com.vivek.expensetrackerapp.domain.models.Transaction

@ExperimentalCoilApi
@Composable
fun TransactionCard(
    transaction: Transaction,
    onTransactionNavigate: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(70.dp)
            .clickable {
                onTransactionNavigate(transaction.transactionId)
            },
        shape = RoundedCornerShape(15),
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onBackground)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ){

                Spacer(modifier = Modifier.width(11.dp))
                Image(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    painter = rememberImagePainter(
                        data = generateAvatarURL(transaction.transactionName),
                        builder = {
                            crossfade(true)
                        },
                    ),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .width(140.dp)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = transaction.transactionName,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)

                    )
                    Text(
                        text = transaction.transactionCreatedOn,
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )

                }
                Text(
                    text = "Rs ${transaction.transactionAmount} /=",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            }
            IconButton(onClick = {
                onTransactionNavigate(transaction.transactionId)
            }) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    tint = Color.Black,
                    contentDescription = "More "
                )

            }
        }

    }

}