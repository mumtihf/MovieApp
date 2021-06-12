package com.mumti.jetpacksubmission

import android.app.Application
import com.mumti.mycore.di.databaseModule
import com.mumti.mycore.di.networkModule
import com.mumti.mycore.di.repositoryModule
import com.mumti.jetpacksubmission.di.useCaseModule
import com.mumti.jetpacksubmission.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}