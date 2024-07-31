package com.example.lesson7

import android.app.Application
import com.example.lesson7.network.HeroesRepository

class HeroesApplication : Application() {
    lateinit var repository: HeroesRepository

    override fun onCreate() {
        super.onCreate()
        instance = this
        repository = HeroesRepository(ApiClient.retrofit)
    }

    companion object {
        private lateinit var instance: HeroesApplication
        fun getApp() = instance
    }
}
