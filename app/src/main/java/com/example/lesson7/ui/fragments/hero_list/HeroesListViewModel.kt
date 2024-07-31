package com.example.lesson7.ui.fragments.hero_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson7.HeroesApplication
import com.example.lesson7.models.Hero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object HeroesListViewModel : ViewModel() {
    private val _uiHeroesState = MutableLiveData<UIHeroesState>(UIHeroesState.Empty)
    private val repository = HeroesApplication.getApp().repository
    val uiHeroesState: LiveData<UIHeroesState> = _uiHeroesState

    fun getHeroes() {
        _uiHeroesState.value = UIHeroesState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiHeroesState.postValue(UIHeroesState.Processing)
                try {
                    val result = repository.getHeroes()
                    _uiHeroesState.postValue(UIHeroesState.Result(result))
                } catch (e: Exception) {
                    _uiHeroesState.postValue(
                        UIHeroesState.Error(e.localizedMessage ?: e.toString())
                    )
                }
            }
        }
    }

    sealed class UIHeroesState {
        data object Empty : UIHeroesState()
        data object Processing : UIHeroesState()
        class Result(val heroes: List<Hero>) : UIHeroesState()
        class Error(val error: String) : UIHeroesState()
    }
}
