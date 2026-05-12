package com.example.flowra.domain.model

data class Expense(
    val id: Int,
    val title: String,
    val amount: Int,
    val category: String,
    val addedDate: Long = System.currentTimeMillis()
)