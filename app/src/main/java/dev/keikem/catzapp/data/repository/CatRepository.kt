package dev.keikem.catzapp.data.repository

import dev.keikem.catzapp.DatabaseHolder
import dev.keikem.catzapp.NetworkHolder
import dev.keikem.catzapp.data.local.entity.LocalCat

class CatRepository {

    private val database = DatabaseHolder.provideDb()
    private val catsApi = NetworkHolder.provideCatApi

    suspend fun loadFromRemote(): String? = catsApi.getCat()?.get(0)?.url
    /*   var urlConnection: HttpsURLConnection? = null
    val imageUrl: String
    try {
        val url = URL("https://api.thecatapi.com/v1/images/search")
        urlConnection = url.openConnection() as HttpsURLConnection
        urlConnection.connect()

        val stream = urlConnection.inputStream
        val reader = BufferedReader(InputStreamReader(stream))
        val result = reader.lines().collect(Collectors.joining())
        val typeAlias = object : TypeToken<List<RemoteCat>>() {}.type
        val convertedResult: List<RemoteCat> = Gson().fromJson(result, typeAlias)
        imageUrl = convertedResult[0].url
    } finally {
        urlConnection?.disconnect()
    }

    return imageUrl*/

    fun loadFromLocal(): String? = database?.catDao()?.get()?.imageUrl

    fun saveToLocal(cat: LocalCat) {
        database?.catDao()?.set(cat)
    }
}