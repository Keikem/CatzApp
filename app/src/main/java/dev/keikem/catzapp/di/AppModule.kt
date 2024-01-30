package dev.keikem.catzapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.keikem.catzapp.data.api.CatsApi
import dev.keikem.catzapp.data.api.DogsApi
import dev.keikem.catzapp.data.local.dao.CatDao
import dev.keikem.catzapp.data.local.dao.DogDao
import dev.keikem.catzapp.data.repository.CatRepository
import dev.keikem.catzapp.data.repository.DogRepository
import dev.keikem.catzapp.domain.usecases.GimmeACatLocalUseCase
import dev.keikem.catzapp.domain.usecases.GimmeACatRemoteUseCase
import dev.keikem.catzapp.domain.usecases.GimmeADogLocalUseCase
import dev.keikem.catzapp.domain.usecases.GimmeADogRemoteUseCase

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDogRepository(dao: DogDao, api: DogsApi): DogRepository = DogRepository(dao, api)

    @Provides
    fun provideCatRepository(dao: CatDao, api: CatsApi): CatRepository = CatRepository(dao, api)

    @Provides
    fun provideLocalCatUseCase(repository: CatRepository): GimmeACatLocalUseCase =
        GimmeACatLocalUseCase(repository)

    @Provides
    fun provideRemoteCatUseCase(repository: CatRepository): GimmeACatRemoteUseCase =
        GimmeACatRemoteUseCase(repository)

    @Provides
    fun provideLocalDogUseCase(repository: DogRepository): GimmeADogLocalUseCase =
        GimmeADogLocalUseCase(repository)

    @Provides
    fun provideRemoteDogUseCase(repository: DogRepository): GimmeADogRemoteUseCase =
        GimmeADogRemoteUseCase(repository)
}
