package com.example.flowra

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.flowra.domain.model.Expense
import com.example.flowra.domain.usecases.GetExpensesUseCase

class MainViewModel(
    private val getExpensesUseCase: GetExpensesUseCase
): ViewModel() {
    private val _expenses = mutableStateOf<List<Expense>>(emptyList())
    val expenses: State<List<Expense>> = _expenses

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        _expenses.value = getExpensesUseCase()
    }
}