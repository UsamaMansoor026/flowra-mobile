package com.example.flowra.data.repository

import com.example.flowra.domain.model.Expense
import com.example.flowra.domain.repository.ExpenseRepository

class ExpenseRepositoryImpl : ExpenseRepository {
    override fun getExpenses(): List<Expense> {
        val now = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L

        return listOf(
            Expense(1, "Fuel", 500, "Transport", now - (2 * 60 * 60 * 1000)),
            Expense(2, "Coffee", 120, "Food", now - (5 * 60 * 60 * 1000)),
            Expense(3, "Internet", 10000, "Bills", now - oneDay),
            Expense(4, "Groceries", 3200, "Food", now - (2 * oneDay)),
            Expense(5, "Biscuit", 200, "Food", now - (10 * oneDay)),
        )
    }
}