package ru.eltech.chucknorrisjokes.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.eltech.chucknorrisjokes.domain.entities.JokeEntity

interface JokesRepository {

    fun loadJokeList(numberOfJokes: Int): Flow<List<JokeEntity>>
}