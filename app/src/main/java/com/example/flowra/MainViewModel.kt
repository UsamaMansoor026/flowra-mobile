package com.example.flowra

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.flowra.domain.model.Bill
import com.example.flowra.domain.model.BillWithStatus
import com.example.flowra.domain.model.Expense
import com.example.flowra.domain.usecases.GetBillsUseCase
import com.example.flowra.domain.usecases.GetExpensesUseCase
import com.example.flowra.domain.usecases.ToggleBillActiveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getBillsUseCase: GetBillsUseCase,
    private val toggleBillActiveUseCase: ToggleBillActiveUseCase
) : ViewModel() {
    private val _expenses = mutableStateOf<List<Expense>>(emptyList())
    val expenses: State<List<Expense>> = _expenses

    private val _bills = MutableStateFlow<List<Bill>>(emptyList())
    val bills: StateFlow<List<Bill>> = _bills.asStateFlow()

    init {
        loadExpenses()
        loadBills()
    }

    private fun loadExpenses() {
        _expenses.value = getExpensesUseCase()
    }

    private fun loadBills() {
       _bills.value = getBillsUseCase()
    }

    fun toggleActive(id: Int) {
        toggleBillActiveUseCase(id)
        loadBills()
    }
}