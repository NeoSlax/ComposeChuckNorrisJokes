package ru.eltech.chucknorrisjokes.domain.usecases

import ru.eltech.chucknorrisjokes.domain.repository.JokesRepository
import javax.inject.Inject

class LoadJokesUseCase @Inject constructor(private val repository: JokesRepository) {

    operator fun invoke(numberOfJokes: Int) = repository.loadJokeList(numberOfJokes)
}