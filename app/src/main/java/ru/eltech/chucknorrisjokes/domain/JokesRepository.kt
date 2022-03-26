package ru.eltech.chucknorrisjokes.domain

import kotlinx.coroutines.flow.Flow

interface JokesRepository {

    fun loadJokeList(numberOfJokes: Int): Flow<List<JokeEntity>>
}