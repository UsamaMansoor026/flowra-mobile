package com.example.flowra.domain.usecases

import com.example.flowra.domain.model.Bill
import com.example.flowra.domain.repository.BillRepository

class GetBillsUseCase(
    private val repository: BillRepository
) {
    operator fun invoke(): List<Bill> = repository.getBills()
}