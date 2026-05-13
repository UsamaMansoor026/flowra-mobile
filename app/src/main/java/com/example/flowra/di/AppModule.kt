package com.example.flowra.di

import org.koin.core.module.dsl.viewModel
import com.example.flowra.MainViewModel
import com.example.flowra.data.repository.BillRepositoryImpl
import com.example.flowra.data.repository.ExpenseRepositoryImpl
import com.example.flowra.domain.repository.BillRepository
import com.example.flowra.domain.repository.ExpenseRepository
import com.example.flowra.domain.usecases.GetBillsUseCase
import com.example.flowra.domain.usecases.GetExpensesUseCase
import com.example.flowra.domain.usecases.ToggleBillActiveUseCase
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<ExpenseRepository> { ExpenseRepositoryImpl() }
    single<BillRepository> { BillRepositoryImpl() }

    // use cases
    factory { GetExpensesUseCase(get()) }
    factory { GetBillsUseCase(get ()) }
    factory { ToggleBillActiveUseCase(get()) }

    // ViewModel
    viewModel { MainViewModel(get(), get(), get()) }
}