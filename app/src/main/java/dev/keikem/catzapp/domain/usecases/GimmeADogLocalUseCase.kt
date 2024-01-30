package dev.keikem.catzapp.domain.usecases

import dev.keikem.catzapp.data.repository.DogRepository
import javax.inject.Inject

class GimmeADogLocalUseCase @Inject constructor(private val repository: DogRepository) {

    fun gimme(): String? = repository.loadFromLocal()
}