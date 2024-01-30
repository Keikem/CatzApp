package dev.keikem.catzapp.data.repository

import dev.keikem.catzapp.data.api.CatsApi
import dev.keikem.catzapp.data.local.dao.CatDao
import dev.keikem.catzapp.data.local.entity.LocalCat
import javax.inject.Inject

class CatRepository @Inject constructor(private val dao: CatDao, private val api: CatsApi) {

    suspend fun loadFromRemote(): String? = api.getCat()?.get(0)?.url
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

    fun loadFromLocal(): String? = dao.get()?.imageUrl

    fun saveToLocal(cat: LocalCat) {
        dao.set(cat)
    }
}