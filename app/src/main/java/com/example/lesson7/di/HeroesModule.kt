package com.example.lesson7.di

import com.example.lesson7.network.HeroesRepository
import org.koin.dsl.module

val heroesModule = module {
    single { ApiClient.retrofit }
    single { HeroesRepository(get()) }
}