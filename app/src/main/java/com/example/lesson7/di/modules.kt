package com.example.lesson7.di

import com.example.lesson7.network.HeroesRepository
import com.example.lesson7.network.ApiClient
import dagger.Module
import dagger.Provides

@Module
class ApiClientModule {
    @Provides
    fun getApiClient(): ApiClient = ApiClient()
}

@Module
class HeroesRepositoryModule {
    @Provides
    fun getHeroesRepository(client: ApiClient): HeroesRepository = HeroesRepository(client)
}
