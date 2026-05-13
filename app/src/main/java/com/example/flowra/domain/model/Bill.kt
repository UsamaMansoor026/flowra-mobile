package com.example.flowra.domain.model

import java.util.Calendar

data class Bill(
    val id: Int,
    val title: String,
    val category: String,
    val amount: Long,
    val dueDate: Int,
    val billingCycle: BillingCycle,
    val isActive: Boolean = true,
    val reminderEnabled: Boolean = true
) {
    // Computed — no need to store or pass separately
    val daysUntilDue: Int
        get() {
            val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            return dueDate - today
        }

    val status: BillStatus
        get() = when {
            daysUntilDue < 0  -> BillStatus.OVERDUE
            daysUntilDue <= 3 -> BillStatus.DUE_SOON
            else              -> BillStatus.UPCOMING
        }
}

enum class BillingCycle(val label: String) {
    MONTHLY("Monthly"),
    YEARLY("Yearly"),
    WEEKLY("Weekly"),
}

enum class BillStatus {
    UPCOMING,   // due date > today
    DUE_SOON,   // due within 3 days
    OVERDUE     // due date < today
}