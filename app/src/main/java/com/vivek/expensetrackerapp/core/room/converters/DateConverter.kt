
package com.vivek.expensetrackerapp.core.room.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }

    @TypeConverter
    fun toDate(value: String?): Date? {
        return value?.let {
            dateFormat.parse(it)
        }
    }
}