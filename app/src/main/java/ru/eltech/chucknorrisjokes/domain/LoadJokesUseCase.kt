package ru.eltech.chucknorrisjokes.domain

import javax.inject.Inject

class LoadJokesUseCase @Inject constructor(private val repository: JokesRepository) {

    operator fun invoke(numberOfJokes: Int) = repository.loadJokeList(numberOfJokes)
}