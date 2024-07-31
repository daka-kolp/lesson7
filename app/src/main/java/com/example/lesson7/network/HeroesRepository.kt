package com.example.lesson7.network

import com.example.lesson7.models.Hero
import retrofit2.Retrofit

class HeroesRepository(private val client: Retrofit) {
    suspend fun getHeroes(): List<Hero> {
        val api = client.create(HeroesInterface::class.java)
        return api.getHeroes()
    }
}
