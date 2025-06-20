
package com.vivek.expensetrackerapp.domain.models

import java.util.*

data class Transaction (
    val transactionId :String,
    val transactionName:String,
    val transactionAmount:Int,
    val transactionCategoryId:String,


    val transactionCreatedAt: String,
    val transactionCreatedOn: String,

    val transactionUpdatedAt:String,
    val transactionUpdatedOn:String,

    )