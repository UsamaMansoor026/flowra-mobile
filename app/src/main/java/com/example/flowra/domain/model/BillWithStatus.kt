package com.example.flowra.domain.model

data class BillWithStatus(
    val bill: Bill,
    val status: BillStatus,
    val daysUntilDue: Int
)