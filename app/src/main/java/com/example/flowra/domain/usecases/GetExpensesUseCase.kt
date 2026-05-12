package com.example.flowra.domain.usecases

import com.example.flowra.domain.model.Expense
import com.example.flowra.domain.repository.ExpenseRepository

class GetExpensesUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): List<Expense> {
        return repository.getExpenses()
    }
}