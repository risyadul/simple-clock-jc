package com.example.simpleclock.di

import android.content.Context
import com.example.simpleclock.data.repository.TimeRepositoryImpl
import com.example.simpleclock.domain.repository.TimeRepository
import com.example.simpleclock.domain.usecase.GetDateUseCase
import com.example.simpleclock.domain.usecase.GetTimeUseCase
import com.example.simpleclock.presentation.screen.clock.ClockViewModel
import com.example.simpleclock.presentation.screen.timer.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repository
    single<TimeRepository> { TimeRepositoryImpl() }

    // Use Cases
    factory { GetTimeUseCase(get()) }
    factory { GetDateUseCase(get()) }

    // ViewModels
    viewModel { ClockViewModel(get(), get()) }
    viewModel { (context: Context) -> TimerViewModel(context) }
} 