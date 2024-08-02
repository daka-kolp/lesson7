package com.example.lesson7.ui.fragments.hero_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson7.HeroesApplication
import com.example.lesson7.models.Hero
import com.example.lesson7.network.HeroesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HeroesListViewModel : ViewModel() {
    private val _uiHeroesState = MutableLiveData<UIHeroesState>(UIHeroesState.Empty)
    val uiHeroesState: LiveData<UIHeroesState> = _uiHeroesState
    @Inject
    lateinit var repository: HeroesRepository

    init {
        HeroesApplication.component.inject(this)
    }

    fun getHeroes() {
        _uiHeroesState.value = UIHeroesState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var value: UIHeroesState = UIHeroesState.Processing
                _uiHeroesState.postValue(value)
                try {
                    val result = repository.getHeroes()
                    value = UIHeroesState.Result(result)
                } catch (e: Exception) {
                    value = UIHeroesState.Error(e.localizedMessage ?: e.toString())
                } finally {
                    _uiHeroesState.postValue(value)
                }
            }
        }
    }

    sealed class UIHeroesState {
        object Empty : UIHeroesState()
        object Processing : UIHeroesState()
        class Result(val heroes: List<Hero>) : UIHeroesState()
        class Error(val error: String) : UIHeroesState()
    }
}
