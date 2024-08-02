package com.example.lesson7.ui.fragments.hero_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson7.models.Hero
import com.example.lesson7.network.HeroesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(private val repository: HeroesRepository) : ViewModel() {
    private val _uiHeroesState = MutableLiveData<UIHeroesState>(UIHeroesState.Empty)
    val uiHeroesState: LiveData<UIHeroesState> = _uiHeroesState

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
        data object Empty : UIHeroesState()
        data object Processing : UIHeroesState()
        class Result(val heroes: List<Hero>) : UIHeroesState()
        class Error(val error: String) : UIHeroesState()
    }
}
