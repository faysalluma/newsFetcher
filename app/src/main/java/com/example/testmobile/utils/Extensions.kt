package com.example.testmobile.utils

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun TextView.getTimeFromDate(dateIso: String) {
    // Convert iso Date to string dateTime
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    val dateTime = LocalDateTime.parse(dateIso, inputFormatter)
    val formattedDateTime = dateTime.format(outputFormatter)

    this.text=formattedDateTime
}