
package com.vivek.expensetrackerapp.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GraphFilterCard(
    modifier: Modifier = Modifier,
    filterName:String,
    isActive:Boolean,
    onClick:(String) -> Unit

    ) {
    Box(
        modifier =Modifier.padding(6.dp).clickable {
            onClick(filterName)
        }
    ){
        if (isActive){
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(5.dp),
                    )
                    .padding(6.dp)

            ) {
                Text(
                    text = filterName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            }
        }else{
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(6.dp)

            ) {
                Text(
                    text = filterName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            }
        }
    }

}