package com.example.flowra.data.repository

import com.example.flowra.domain.model.Bill
import com.example.flowra.domain.model.BillingCycle
import com.example.flowra.domain.model.Category
import com.example.flowra.domain.repository.BillRepository

class BillRepositoryImpl : BillRepository {
    private val bills = mutableListOf(
        Bill(
            1,
            "Netflix",
            Category.ENTERTAINMENT,
            1200,
            3,
            BillingCycle.MONTHLY,
            isActive = true,
            true
        ),
        Bill(
            2,
            "House Rent",
            Category.BILLS,
            25000,
            5,
            BillingCycle.MONTHLY,
            isActive = true,
            true
        ),
        Bill(
            3,
            "Electricity",
            Category.BILLS,
            3500,
            1,
            BillingCycle.MONTHLY,
            isActive = true,
            true
        ),
        Bill(
            4,
            "Spotify",
            Category.ENTERTAINMENT,
            350,
            18,
            BillingCycle.MONTHLY,
            isActive = true,
            false
        ),
        Bill(5, "Internet", Category.BILLS, 2500, 20, BillingCycle.MONTHLY, isActive = true, true),
        Bill(
            6,
            "Gym Membership",
            Category.HEALTH,
            2000,
            22,
            BillingCycle.MONTHLY,
            isActive = true,
            true
        ),
        Bill(
            7,
            "Car Insurance",
            Category.TRANSPORT,
            15000,
            10,
            BillingCycle.YEARLY,
            isActive = true,
            true
        ),
        Bill(
            8,
            "YouTube Premium",
            Category.ENTERTAINMENT,
            189,
            25,
            BillingCycle.MONTHLY,
            isActive = false,
            false
        ),
    )

    override fun getBills(): List<Bill> = bills.toList()

    override fun toggleActive(id: Int) {
        val index = bills.indexOfFirst { it.id == id }
        if (index != -1) bills[index] = bills[index].copy(isActive = !bills[index].isActive)
    }
}