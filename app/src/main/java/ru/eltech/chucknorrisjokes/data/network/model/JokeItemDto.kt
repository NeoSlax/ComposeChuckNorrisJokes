package ru.eltech.chucknorrisjokes.data.network.model


import com.google.gson.annotations.SerializedName

data class JokeItemDto(
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("joke")
    val joke: String
)