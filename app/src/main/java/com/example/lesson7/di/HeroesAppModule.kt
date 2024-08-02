package com.example.lesson7.di

import com.example.lesson7.network.ApiClient
import com.example.lesson7.network.HeroesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HeroesAppModule {
    @Provides
    @Singleton
    fun getApiClient(): ApiClient = ApiClient()

    @Provides
    @Singleton
    fun getHeroesRepository(client: ApiClient): HeroesRepository = HeroesRepository(client)
}
