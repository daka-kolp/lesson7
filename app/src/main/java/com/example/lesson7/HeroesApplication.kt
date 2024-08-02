package com.example.lesson7

import android.app.Application
import com.example.lesson7.di.heroesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HeroesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HeroesApplication)
            modules(heroesModule)
        }
    }
}
