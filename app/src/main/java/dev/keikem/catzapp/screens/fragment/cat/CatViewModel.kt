package dev.keikem.catzapp.screens.fragment.cat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.keikem.catzapp.data.repository.CatRepository
import dev.keikem.catzapp.domain.usecases.GimmeACatLocalUseCase
import dev.keikem.catzapp.domain.usecases.GimmeACatRemoteUseCase
import dev.keikem.catzapp.domain.usecases.GimmeADogRemoteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val localUseCase: GimmeACatLocalUseCase,
    private val remoteUseCase: GimmeACatRemoteUseCase
) : ViewModel() {

    private var _state: MutableLiveData<State?> = MutableLiveData(null) // flow
    val state: LiveData<State?> = _state

    //UDF = Undirectonal data flow
    sealed class State {
        data class ImageLoaded(val url: String) : State()
        data class Error(val message: String) : State()
    }

    init {
        loadFromDatabase()
    }

    private fun loadFromDatabase() {
        Thread {
            val im = localUseCase.gimme()
            im?.let { image -> _state.postValue(State.ImageLoaded(url = image)) }
        }.start()
    }

    /* fun loadFromRemoteThread() {
         //Thread - отдельный поток выполнения, он отвечает за то где будет выполнятся операция
         Thread {
             Thread.sleep(5000)
             _imageUrl.postValue(gimmeACatRemoteUseCase.gimme())
         }.start()
     } */

    fun loadFromRemote() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

            _state.postValue(
                State.Error(
                    message = throwable.message ?: "Не удалось получить картинку"
                )
            )
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            remoteUseCase.gimme()?.let { _state.postValue(State.ImageLoaded(url = it)) }
                ?: _state.postValue(State.Error(message = "Значение не может быть пустым"))
        }
    }
}