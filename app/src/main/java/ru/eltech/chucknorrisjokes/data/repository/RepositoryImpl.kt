package ru.eltech.chucknorrisjokes.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.eltech.chucknorrisjokes.data.mapper.JokeMapper
import ru.eltech.chucknorrisjokes.data.network.ApiService
import ru.eltech.chucknorrisjokes.domain.JokeEntity
import ru.eltech.chucknorrisjokes.domain.JokesRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val mapper: JokeMapper,
    private val apiService: ApiService
) : JokesRepository {

    override fun loadJokeList(numberOfJokes: Int): Flow<List<JokeEntity>> {
        return flow {
            emit(apiService.getJokeList(numberOfJokes).value.map {
                mapper.mapJokeItemDtoToJokeEntity(
                    it
                )
            })
        }.flowOn(Dispatchers.IO)
    }
}