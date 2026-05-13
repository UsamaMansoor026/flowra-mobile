package com.example.flowra.domain.repository

import com.example.flowra.domain.model.Bill

interface BillRepository {
    fun getBills(): List<Bill>
    fun toggleActive(id: Int)
}