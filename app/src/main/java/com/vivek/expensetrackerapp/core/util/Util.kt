
package com.vivek.expensetrackerapp.core.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import com.google.android.play.core.review.ReviewManagerFactory
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.*
import java.util.*


fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}

fun localDateTimeToDate(localDate: LocalDate, localTime: LocalTime): Date {
    val localDateTime = LocalDateTime.of(localDate, localTime)
    val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
    return Date.from(instant)
}
fun randomColorCode(): String {
    val random = Random()
    val nextInt = random.nextInt(0xffffff + 1)
    return String.format("#%06x", nextInt).drop(1).capitalize(Locale.ROOT)
}

fun generateAvatarURL(name:String):String{
    val splitname = name.split(" ").joinToString("+")
    val color = randomColorCode()
    return "https://ui-avatars.com/api/?background=${color}&color=fff&name=${splitname}&bold=true&fontsize=0.6&rounded=true"

}

fun getAppVersionName(context: Context): String {
    var versionName = ""
    try {
        val info = context.packageManager?.getPackageInfo(context.packageName, 0)
        versionName = info?.versionName ?: ""
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.e(e.message)
    }
    return versionName
}


fun getNumericInitialValue(value:Int):String{
    return if (value == 0){
        ""
    }else{
        value.toString()
    }
}


fun getFormattedDate(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calender.timeInMillis)
}

fun dateValidator(): (Long) -> Boolean {
    return { timeInMillis ->
        val endCalenderDate = Calendar.getInstance()
        endCalenderDate.timeInMillis = timeInMillis
        endCalenderDate.set(Calendar.DATE, Calendar.DATE + 20)
        timeInMillis > Calendar.getInstance().timeInMillis && timeInMillis < endCalenderDate.timeInMillis
    }
}



fun convertTimeMillisToLocalDate(timeMillis: Long): LocalDate {
    // Create an Instant from the timeMillis value
    val instant = Instant.ofEpochMilli(timeMillis)

    // Specify the desired time zone if needed (default is your system's time zone)
    val zoneId = ZoneId.systemDefault() // Or any specific ZoneId like ZoneId.of("UTC")

    // Convert the Instant to a LocalDate in the specified time zone
    return instant.atZone(zoneId).toLocalDate()
}

fun truncate(str:String, n:Int):String {
    if (str.length > n){
        return str.substring(0, n - 1) + "...."
    }else{
        return str
    }
}

fun Context.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}


fun showReviewDialog(activity: Activity, onComplete: () -> Unit, onFailure: () -> Unit) {
    val reviewManager = ReviewManagerFactory.create(activity.applicationContext)
    reviewManager.requestReviewFlow()
        .addOnCompleteListener {
            if (it.isSuccessful) {
                reviewManager.launchReviewFlow(activity, it.result)
                    .addOnSuccessListener {
                        onComplete()
                    }
                    .addOnFailureListener {
                        onFailure()
                    }
            }else{
                onFailure()
            }
        }
        .addOnFailureListener {
            it.printStackTrace()
            onFailure()

        }
}