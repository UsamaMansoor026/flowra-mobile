package com.example.flowra.data.repository

import com.example.flowra.domain.model.Category
import com.example.flowra.domain.model.Expense
import com.example.flowra.domain.repository.ExpenseRepository

class ExpenseRepositoryImpl : ExpenseRepository {
    override fun getExpenses(): List<Expense> {
        val now = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L

        return listOf(
            Expense(1,  "Fuel",      500, Category.TRANSPORT,     now - (2 * 60 * 60 * 1000)),
            Expense(2,  "Coffee",    120,   Category.FOOD,          now - (5 * 60 * 60 * 1000)),
            Expense(3,  "Internet",  10000, Category.BILLS,         now - oneDay),
            Expense(4,  "Groceries", 3200,  Category.FOOD,          now - (2 * oneDay)),
            Expense(5,  "Biscuit",   200,   Category.FOOD,          now - (10 * oneDay)),
            Expense(6,  "Fuel",      120,   Category.TRANSPORT,     now - (12 * oneDay)),
            Expense(7,  "Internet",  10000, Category.BILLS,         now - oneDay),
            Expense(8,  "Groceries", 3200,  Category.FOOD,          now - (2 * oneDay)),
            Expense(9,  "Biscuit",   200,   Category.FOOD,          now - (10 * oneDay)),
            Expense(10, "Fuel",      500,   Category.TRANSPORT,     now - (2 * 60 * 60 * 1000)),
        )
    }
}