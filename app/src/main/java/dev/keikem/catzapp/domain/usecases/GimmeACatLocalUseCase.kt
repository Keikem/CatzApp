package dev.keikem.catzapp.domain.usecases

import dev.keikem.catzapp.data.repository.CatRepository
import javax.inject.Inject

//Класс, репрезентующии получение данных с базы и преобразование в то с чем мы работаем
class GimmeACatLocalUseCase @Inject constructor(private val repository: CatRepository) {

    fun gimme(): String? = repository.loadFromLocal()
}