package dev.keikem.catzapp.data.repository

import dev.keikem.catzapp.data.api.DogsApi
import dev.keikem.catzapp.data.local.dao.DogDao
import dev.keikem.catzapp.data.local.entity.LocalDog
import javax.inject.Inject

class DogRepository @Inject constructor(private val dao: DogDao, private val api: DogsApi) {

    suspend fun loadFromRemote(): String? = api.getDog()?.message
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

    fun loadFromLocal(): String? = dao.get()?.imageUrl

    fun saveToLocal(dog: LocalDog) {
        dao.set(dog)
    }
}