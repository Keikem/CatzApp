package dev.keikem.catzapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dev.keikem.catzapp.data.api.CatsApi
import dev.keikem.catzapp.data.api.DogsApi
import dev.keikem.catzapp.data.local.Database
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

//Класс, репрезентирующий само приложение
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseHolder.createDatabase(applicationContext)
    }
}

//Обьект, отвечающии за создание/хранение бд
object DatabaseHolder {

    private var _database: Database? = null

    fun createDatabase(context: Context) {
        if (_database == null) {
            _database = Room.databaseBuilder(
                context,
                Database::class.java,
                "database.db"
            ).build()
        }
    }

    fun provideDb(): Database? = _database

}

//Объект отвечающии за экземпляры http клиента и сущности retrofit
object NetworkHolder {

    private val gson: Gson = Gson()

    private val okHttpClient = OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).build()

    private val catRetrofit =
        Retrofit.Builder().baseUrl("https://api.thecatapi.com/")
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            ).build()

    private val dogRetrofit = Retrofit.Builder().baseUrl("https://dog.ceo/")
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(gson)
        ).build()

    val provideCatApi: CatsApi = catRetrofit.create()

    val provideDogApi: DogsApi = dogRetrofit.create(DogsApi::class.java)
}