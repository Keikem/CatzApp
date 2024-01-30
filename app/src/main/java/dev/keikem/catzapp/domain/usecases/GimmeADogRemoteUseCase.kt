package dev.keikem.catzapp.domain.usecases

import dev.keikem.catzapp.data.local.entity.LocalDog
import dev.keikem.catzapp.data.repository.DogRepository
import javax.inject.Inject

class GimmeADogRemoteUseCase @Inject constructor(private val repository: DogRepository) {

    suspend fun gimme(): String? {
        val dogUrl = repository.loadFromRemote()
        dogUrl?.let { repository.saveToLocal(LocalDog(id = "1", imageUrl = it)) }

        return dogUrl
    }
}