package dev.keikem.catzapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import dev.keikem.catzapp.data.api.CatsApi
import dev.keikem.catzapp.data.api.DogsApi
import dev.keikem.catzapp.data.local.Database
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

//Класс, репрезентирующий само приложение
@HiltAndroidApp
class App : Application()