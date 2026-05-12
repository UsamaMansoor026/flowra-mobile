package com.example.flowra.domain.repository

import com.example.flowra.domain.model.Expense

interface ExpenseRepository {
    fun getExpenses(): List<Expense>
}