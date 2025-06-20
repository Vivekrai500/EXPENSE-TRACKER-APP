package com.vivek.expensetrackerapp.core.alarm

import java.time.LocalDateTime

data class AlarmItem(
    val time:LocalDateTime,
    val message:String
)