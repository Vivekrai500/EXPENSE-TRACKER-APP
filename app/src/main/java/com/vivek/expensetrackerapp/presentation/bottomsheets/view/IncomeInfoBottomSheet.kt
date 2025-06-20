
package com.vivek.expensetrackerapp.presentation.bottomsheets.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vivek.expensetrackerapp.core.util.TestTags
import com.vivek.expensetrackerapp.domain.models.Income
import com.vivek.expensetrackerapp.presentation.bottomsheets.viewModels.IncomeInfoBottomSheetViewModel
import com.vivek.expensetrackerapp.R

@Composable
fun IncomeInfoBottomSheet(
    activeIncomeId:String?,
    viewModel:IncomeInfoBottomSheetViewModel = hiltViewModel(),
    
) {
    val income by viewModel.income.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = activeIncomeId){
        if (activeIncomeId != null) {
            viewModel.getIncomeById(incomeId = activeIncomeId)
        }
    }
    IncomeInfoBottomSheetContent(
        incomeNullable = income,
        deleteIncome = {
            if (activeIncomeId != null) {
                viewModel.deleteIncome(incomeId = activeIncomeId)
            }
        }
    )

}

@Composable
fun IncomeInfoBottomSheetContent(
    incomeNullable: Income?,
    deleteIncome:() -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ){
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onBackground)
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            incomeNullable?.let { income: Income ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = "Income Name : ${income.incomeName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = "Income Amount : ${income.incomeAmount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = "Income Created At : ${income.incomeCreatedAt}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(tag = TestTags.CREATE_TRANSACTION_CATEGORY_SAVE_BUTTON)
                    ,
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    onClick = {
                        deleteIncome()
                    }
                ){
                    Text(
                        text = stringResource(id = R.string.delete_income),
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )

                }
            }


        }
    }


}

@Preview
@Composable
fun IncomeInfoBottomSheetPreview(){
    IncomeInfoBottomSheetContent(
        incomeNullable = Income(
            incomeName = "Test",
            incomeAmount = 3000,
            incomeId = "dkfjfjf",
            incomeCreatedAt = "djdhddjdk"
        ),
        deleteIncome = {

        }
    )
}