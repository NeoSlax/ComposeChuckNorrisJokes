package ru.eltech.chucknorrisjokes.data.network.model


import com.google.gson.annotations.SerializedName

data class JokeListReponseDto(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: List<JokeItemDto>
)