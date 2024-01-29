package dev.keikem.catzapp.screens.fragment.cat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.keikem.catzapp.domain.usecases.GimmeACatLocalUseCase
import dev.keikem.catzapp.domain.usecases.GimmeACatRemoteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {

    private val gimmeACatRemoteUseCase: GimmeACatRemoteUseCase by lazy { GimmeACatRemoteUseCase() }
    private val gimmeACatLocalUseCase: GimmeACatLocalUseCase by lazy { GimmeACatLocalUseCase() }

    private var _imageUrl: MutableLiveData<String?> = MutableLiveData("")
    val imageUrl: LiveData<String?> = _imageUrl

    init {
        loadFromDatabase()
    }

    private fun loadFromDatabase() {
        Thread {
            val im = gimmeACatLocalUseCase.gimme()
            im?.let { image -> _imageUrl.postValue(image) }
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
        val coroutinExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            _imageUrl.postValue(null)
        }
        viewModelScope.launch(Dispatchers.IO + coroutinExceptionHandler) {
            _imageUrl.postValue( gimmeACatRemoteUseCase.gimme())
        }
    }
}