package dev.keikem.catzapp.screens.fragment.dog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.keikem.catzapp.data.repository.DogRepository
import dev.keikem.catzapp.domain.usecases.GimmeACatLocalUseCase
import dev.keikem.catzapp.domain.usecases.GimmeACatRemoteUseCase
import dev.keikem.catzapp.domain.usecases.GimmeADogLocalUseCase
import dev.keikem.catzapp.domain.usecases.GimmeADogRemoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val localUseCase: GimmeADogLocalUseCase,
    private val remoteUseCase: GimmeADogRemoteUseCase
) : ViewModel() {


    private var _imageUrl: MutableLiveData<String> = MutableLiveData("")
    val imageUrl: LiveData<String> = _imageUrl

    init {
        loadFromLocal()
    }

    private fun loadFromLocal() {
        Thread {
            val im = localUseCase.gimme()
            im?.let { image -> _imageUrl.postValue(image) }
        }.start()
    }

    /* fun loadFromRemote() {
        //Thread - отдельный поток выполнения, он отвечает за то где будет выполнятся операция
        Thread {
            Thread.sleep(5000)
            _imageUrl.postValue(gimmeADogRemoteUseCase.gimme())
        }.start()
    } */

    fun loadFromRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            //delay(5000)
            _imageUrl.postValue(remoteUseCase.gimme())
        }
    }

}