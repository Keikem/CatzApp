package dev.keikem.catzapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.keikem.catzapp.data.local.Database
import dev.keikem.catzapp.data.local.dao.CatDao
import dev.keikem.catzapp.data.local.dao.DogDao

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database = Room.databaseBuilder(
        context,
        Database::class.java,
        "database.db"
    ).build()

    @Provides
    fun provideCatDao(database: Database): CatDao = database.catDao()

    @Provides
    fun provideDogDao(database: Database): DogDao = database.dogDao()
}