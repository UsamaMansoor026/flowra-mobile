package com.example.flowra.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatExpenseDate(timestamp: Long): String {
    val now = Calendar.getInstance()
    val expenseDate = Calendar.getInstance().apply { timeInMillis = timestamp }

    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    return when {
        // Today
        now.get(Calendar.YEAR) == expenseDate.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == expenseDate.get(Calendar.DAY_OF_YEAR) -> {
            "Today, ${timeFormat.format(Date(timestamp))}"
        }
        // Yesterday
        now.get(Calendar.YEAR) == expenseDate.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) - expenseDate.get(Calendar.DAY_OF_YEAR) == 1 -> {
            "Yesterday, ${timeFormat.format(Date(timestamp))}"
        }
        // Older
        else -> {
            "${dateFormat.format(Date(timestamp))}, ${timeFormat.format(Date(timestamp))}"
        }
    }
}