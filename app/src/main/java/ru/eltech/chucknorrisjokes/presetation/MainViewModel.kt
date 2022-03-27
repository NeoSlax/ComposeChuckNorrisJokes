package ru.eltech.chucknorrisjokes.presetation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.eltech.chucknorrisjokes.domain.entities.JokeEntity
import ru.eltech.chucknorrisjokes.domain.usecases.LoadJokesUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loadJokesUseCase: LoadJokesUseCase
) : ViewModel() {

    private val _jokeList = MutableLiveData<List<JokeEntity>>()
    val jokeList: LiveData<List<JokeEntity>>
        get() = _jokeList

    fun loadJokeList(count: Int) {
        viewModelScope.launch {
            loadJokesUseCase(count).collect { _jokeList.postValue(it) }
        }

    }
}