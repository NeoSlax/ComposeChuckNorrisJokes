package ru.eltech.chucknorrisjokes.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.eltech.chucknorrisjokes.data.network.model.JokeListReponseDto

interface ApiService {

    @GET("jokes/random/{count}")
    suspend fun getJokeList(
        @Path("count") count: Int
    ): JokeListReponseDto
}