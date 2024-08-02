package com.example.lesson7.di

import com.example.lesson7.ui.fragments.hero_list.HeroesListViewModel
import dagger.Component

@Component(modules = [ApiClientModule::class, HeroesRepositoryModule::class])
interface HeroesComponent {
    fun inject(model: HeroesListViewModel)
}
