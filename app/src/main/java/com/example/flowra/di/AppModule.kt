package com.example.flowra.di

import org.koin.core.module.dsl.viewModel
import com.example.flowra.MainViewModel
import com.example.flowra.data.repository.ExpenseRepositoryImpl
import com.example.flowra.domain.repository.ExpenseRepository
import com.example.flowra.domain.usecases.GetExpensesUseCase
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<ExpenseRepository> { ExpenseRepositoryImpl() }

    // use cases
    factory { GetExpensesUseCase(get()) }

    // ViewModel
    viewModel { MainViewModel(get()) }
}