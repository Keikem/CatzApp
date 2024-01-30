package dev.keikem.catzapp.domain.usecases

import dev.keikem.catzapp.data.local.entity.LocalCat
import dev.keikem.catzapp.data.repository.CatRepository
import javax.inject.Inject

//Класс, репрезентующии получение данных с бека и преобразование в то с чем мы работаем, а также сохранение в базу
class GimmeACatRemoteUseCase @Inject constructor(private val repository: CatRepository) {

    suspend fun gimme(): String? {
        val catUrl = repository.loadFromRemote()
        catUrl?.let { repository.saveToLocal(LocalCat(id = "1", imageUrl = it)) }
        return catUrl
    }
}