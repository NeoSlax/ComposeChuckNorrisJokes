package ru.eltech.chucknorrisjokes.domain.entities

data class JokeEntity(
    val id: Int,
    val joke: String,
    val category: List<String>,
    val explicit: Boolean = false
)
