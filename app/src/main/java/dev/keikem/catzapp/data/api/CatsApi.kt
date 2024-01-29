package dev.keikem.catzapp.data.api

import dev.keikem.catzapp.data.remote.RemoteCat
import retrofit2.http.GET

interface CatsApi {

    @GET("/v1/images/search")
    suspend fun getCat(): List<RemoteCat>?
}