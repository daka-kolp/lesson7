package com.example.lesson7

import android.app.Application
import com.example.lesson7.di.DaggerHeroesComponent
import com.example.lesson7.di.HeroesComponent

class HeroesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerHeroesComponent.create()
    }

    companion object {
        lateinit var component: HeroesComponent
    }
}
