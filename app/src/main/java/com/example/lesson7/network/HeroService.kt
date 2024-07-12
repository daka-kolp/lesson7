package com.example.lesson7.network

import com.example.lesson7.models.Hero
import io.reactivex.Single
import retrofit2.http.GET

interface HeroService {
    @GET("all.json")
    fun getHeroes(): Single<List<Hero>>
}
