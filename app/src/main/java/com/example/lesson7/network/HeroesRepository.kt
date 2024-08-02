package com.example.lesson7.network

import com.example.lesson7.models.Hero

class HeroesRepository(private val client: ApiClient) {
    suspend fun getHeroes(): List<Hero> {
        val api = client.retrofit.create(HeroesInterface::class.java)
        return api.getHeroes()
    }
}
