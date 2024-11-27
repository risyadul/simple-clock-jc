package com.example.simpleclock.di

import android.app.AlarmManager
import android.content.Context
import androidx.room.Room
import com.example.simpleclock.data.database.AppDatabase
import com.example.simpleclock.data.repository.AlarmRepositoryImpl
import com.example.simpleclock.data.repository.TimeRepositoryImpl
import com.example.simpleclock.domain.repository.AlarmRepository
import com.example.simpleclock.domain.repository.TimeRepository
import com.example.simpleclock.domain.usecase.GetDateUseCase
import com.example.simpleclock.domain.usecase.GetTimeUseCase
import com.example.simpleclock.domain.usecase.ScheduleAlarmUseCase
import com.example.simpleclock.presentation.screen.alarm.AlarmViewModel
import com.example.simpleclock.presentation.screen.clock.ClockViewModel
import com.example.simpleclock.presentation.screen.timer.TimerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
        .build()
    }

    // DAOs
    single { 
        get<AppDatabase>().alarmDao() 
    }

    // System Services
    single<AlarmManager> { 
        androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager 
    }

    // Repositories
    single<TimeRepository> { 
        TimeRepositoryImpl() 
    }
    
    single<AlarmRepository> { 
        AlarmRepositoryImpl(alarmDao = get()) 
    }

    // Use Cases
    single { 
        GetTimeUseCase(repository = get()) 
    }
    
    single { 
        GetDateUseCase(repository = get()) 
    }
    
    single { 
        ScheduleAlarmUseCase(
            context = androidContext(),
            alarmManager = get()
        ) 
    }

    // ViewModels
    viewModel { 
        ClockViewModel(
            getTimeUseCase = get(),
            getDateUseCase = get()
        ) 
    }
    
    viewModel { 
        TimerViewModel(context = androidContext()) 
    }
    
    viewModel { 
        AlarmViewModel(
            repository = get<AlarmRepository>(),
            scheduleAlarmUseCase = get<ScheduleAlarmUseCase>()
        ) 
    }
} 