package dev.keikem.catzapp.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.keikem.catzapp.data.api.CatsApi
import dev.keikem.catzapp.data.api.DogsApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    //Base provides

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).build()


    //retrofit provides

    @Provides
    @Named("CatRetrofit")
    fun provideCatRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder().baseUrl("https://api.thecatapi.com/")
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            ).build()

    @Provides
    @Named("DogRetrofit")
    fun provideDogRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder().baseUrl("https://dog.ceo/")
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            ).build()

    //Api provides

    @Provides
    fun provideCatApi(@Named("CatRetrofit") retrofit: Retrofit): CatsApi = retrofit.create()

    @Provides
    fun provideDogApi(@Named("DogRetrofit") retrofit: Retrofit): DogsApi =
        retrofit.create(DogsApi::class.java)
}