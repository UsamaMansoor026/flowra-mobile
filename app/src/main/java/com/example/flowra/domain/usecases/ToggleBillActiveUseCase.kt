package com.example.flowra.domain.usecases

import com.example.flowra.domain.repository.BillRepository

class ToggleBillActiveUseCase(
    private val repository: BillRepository
) {
    operator fun invoke(id: Int) = repository.toggleActive(id)
}