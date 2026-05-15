package com.example.flowra.domain.model

import java.util.Calendar
import java.util.Date

data class Bill(
    val id: Int,
    val title: String,
    val category: String,
    val amount: Long,
    val dueDate: Int,
    val billingCycle: BillingCycle,
    val isActive: Boolean = true,
    val reminderEnabled: Boolean = true,
    val isPaid: Boolean = false,           // ← splits Due vs Active Flow
    val lastPaidDate: Date? = null         // ← tracks when last paid
) {
    val nextDueDate: Date
        get() {
            val today = Calendar.getInstance()
            val due = Calendar.getInstance()
            due.set(Calendar.DAY_OF_MONTH, dueDate)
            due.set(Calendar.HOUR_OF_DAY, 0)
            due.set(Calendar.MINUTE, 0)
            due.set(Calendar.SECOND, 0)
            due.set(Calendar.MILLISECOND, 0)

            // If due date already passed this month → next month
            if (due.timeInMillis <= today.timeInMillis) {
                due.add(Calendar.MONTH, 1)
            }
            return due.time
        }

    val daysUntilDue: Int
        get() {
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val due = Calendar.getInstance().apply {
                time = nextDueDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val diffMs = due.timeInMillis - today.timeInMillis
            return (diffMs / (1000 * 60 * 60 * 24)).toInt()
        }

    val status: BillStatus
        get() = when {
            daysUntilDue < 0  -> BillStatus.OVERDUE
            daysUntilDue <= 3 -> BillStatus.DUE_SOON
            else              -> BillStatus.UPCOMING
        }

    val dueDateLabel: String
        get() = when {
            daysUntilDue < 0  -> "Overdue by ${-daysUntilDue} day${if (-daysUntilDue > 1) "s" else ""}"
            daysUntilDue == 0 -> "Due today"
            daysUntilDue == 1 -> "Due tomorrow"
            else              -> "Due in $daysUntilDue days"
        }

    val nextPaymentLabel: String
        get() {
            val cal = Calendar.getInstance().apply { time = nextDueDate }
            val month = cal.getDisplayName(
                Calendar.MONTH,
                Calendar.SHORT,
                java.util.Locale.getDefault()
            )
            val day = cal.get(Calendar.DAY_OF_MONTH)
            return "Next: $month $day • ${billingCycle.label}"
        }
}

enum class BillingCycle(val label: String) {
    MONTHLY("Monthly"),
    YEARLY("Yearly"),
    WEEKLY("Weekly"),
}

enum class BillStatus {
    UPCOMING,
    DUE_SOON,
    OVERDUE
}