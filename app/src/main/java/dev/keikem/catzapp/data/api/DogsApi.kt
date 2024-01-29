package dev.keikem.catzapp.data.api

import dev.keikem.catzapp.data.remote.RemoteDog
import retrofit2.Call
import retrofit2.http.GET

interface DogsApi {

    @GET("/api/breeds/image/random")
    suspend fun getDog(): RemoteDog?
}