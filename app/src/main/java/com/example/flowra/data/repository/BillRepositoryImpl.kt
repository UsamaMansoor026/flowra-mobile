package com.example.flowra.data.repository

import com.example.flowra.domain.model.Bill
import com.example.flowra.domain.model.BillingCycle
import com.example.flowra.domain.model.Category
import com.example.flowra.domain.repository.BillRepository

class BillRepositoryImpl : BillRepository {
    private val bills = mutableListOf(
        Bill(
            id = 1,
            title = "Netflix",
            category = Category.ENTERTAINMENT,
            amount = 1200,
            dueDate = 3,
            billingCycle = BillingCycle.MONTHLY,
            isActive = true,
            reminderEnabled = true,
            isPaid = false
        ),
        Bill(
            id = 2,
            title = "House Rent",
            category = Category.BILLS,
            amount = 25000,
            dueDate = 5,
            billingCycle = BillingCycle.MONTHLY,
            isActive = true,
            reminderEnabled = true,
            isPaid = false
        ),
        Bill(
            id = 3,
            title = "Electricity",
            category = Category.BILLS,
            amount = 3500,
            dueDate = 1,
            billingCycle = BillingCycle.MONTHLY,
            isActive = true,
            reminderEnabled = true,
            isPaid = false
        ),
        Bill(
            id = 4,
            title = "Spotify",
            category = Category.ENTERTAINMENT,
            amount = 350,
            dueDate = 18,
            billingCycle = BillingCycle.MONTHLY,
            isActive = true,
            reminderEnabled = false,
            isPaid = true
        ),
        Bill(
            id = 5,
            title = "Internet",
            category = Category.BILLS,
            amount = 2500,
            dueDate = 20,
            billingCycle = BillingCycle.MONTHLY,
            isActive = true,
            reminderEnabled = true,
            isPaid = true
        ),
        Bill(
            id = 6,
            title = "Gym Membership",
            category = Category.HEALTH,
            amount = 2000,
            dueDate = 22,
            billingCycle = BillingCycle.MONTHLY,
            isActive = true,
            reminderEnabled = true,
            isPaid = true
        ),
        Bill(
            id = 7,
            title = "Car Insurance",
            category = Category.TRANSPORT,
            amount = 15000,
            dueDate = 10,
            billingCycle = BillingCycle.YEARLY,
            isActive = true,
            reminderEnabled = true,
            isPaid = false
        ),
        Bill(
            id = 8,
            title = "YouTube Premium",
            category = Category.ENTERTAINMENT,
            amount = 189,
            dueDate = 25,
            billingCycle = BillingCycle.MONTHLY,
            isActive = false,
            reminderEnabled = false,
            isPaid = false
        ),
    )

    override fun getBills(): List<Bill> = bills.toList()

    override fun toggleActive(id: Int) {
        val index = bills.indexOfFirst { it.id == id }
        if (index != -1) bills[index] = bills[index].copy(isActive = !bills[index].isActive)
    }
}