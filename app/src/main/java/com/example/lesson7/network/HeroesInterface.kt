package com.example.lesson7.network

import com.example.lesson7.models.Hero
import retrofit2.http.GET

interface HeroesInterface {
    @GET("all.json")
    suspend fun getHeroes(): List<Hero>
}
