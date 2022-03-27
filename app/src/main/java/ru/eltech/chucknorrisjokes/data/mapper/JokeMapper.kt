package ru.eltech.chucknorrisjokes.data.mapper

import ru.eltech.chucknorrisjokes.data.network.model.JokeItemDto
import ru.eltech.chucknorrisjokes.domain.entities.JokeEntity
import javax.inject.Inject

class JokeMapper @Inject constructor() {

    fun mapJokeItemDtoToJokeEntity(dto: JokeItemDto): JokeEntity {
        return JokeEntity(
            id = dto.id,
            joke = dto.joke,
            category = dto.categories,
            explicit = dto.categories.contains(EXPLICIT_KEY)
        )
    }

    companion object {
        private const val EXPLICIT_KEY = "explicit"
    }
}