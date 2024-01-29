package dev.keikem.catzapp.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.keikem.catzapp.DatabaseHolder
import dev.keikem.catzapp.NetworkHolder
import dev.keikem.catzapp.data.api.DogsApi
import dev.keikem.catzapp.data.local.Database
import dev.keikem.catzapp.data.local.entity.LocalDog
import dev.keikem.catzapp.data.remote.RemoteDog
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DogRepository {

    private val database: Database? = DatabaseHolder.provideDb()
    private val dogApi: DogsApi = NetworkHolder.provideDogApi

    suspend fun loadFromRemote(): String? = dogApi.getDog()?.message
      /*  var urlConnection: HttpsURLConnection? = null
        val imageUrl: String
        try {
            val url = URL("https://dog.ceo/api/breeds/image/random")
            urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.connect()

            val stream = urlConnection.inputStream
            val reader = BufferedReader(InputStreamReader(stream))
            val result = reader.lines().collect(Collectors.joining())
            val typeAlias = object : TypeToken<RemoteDog>() {}.type
            val convertedResult: RemoteDog = Gson().fromJson(result, typeAlias)
            imageUrl = convertedResult.message
        } finally {
            urlConnection?.disconnect()
        }

        return imageUrl
    } */

    fun loadFromLocal(): String? = database?.dogDao()?.get()?.imageUrl

    fun saveToLocal(dog: LocalDog) {
        database?.dogDao()?.set(dog)
    }
}