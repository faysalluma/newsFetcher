package com.example.testmobile.presentation.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import com.example.testmobile.di.DispatcherDefault
import com.example.testmobile.domain.usecases.GetArticleStateUseCase
import com.example.testmobile.domain.usecases.GetArticleTaskUseCase
import com.example.testmobile.utils.Failure
import com.example.testmobile.utils.right
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val getArticleTaskUseCase: GetArticleTaskUseCase,
    private val getArticleStateUseCase: GetArticleStateUseCase,
    @DispatcherDefault defaultDispatcher: CoroutineDispatcher
        ) : ViewModel() {

    // Livedatas
    private val _error = MutableLiveData<Failure?>(null)
    val error: LiveData<Failure?> = _error

    // Data Flows
    val articles = Transformations.map(getArticleStateUseCase.observe(viewModelScope.coroutineContext+ defaultDispatcher)){ it?.right()}

    // Process
    fun getArticleList() {
        getArticleTaskUseCase.execute(viewModelScope, Unit) {
            it.apply(::handleFailure, ::handleSampleRetrieved)
        }
    }

    private fun handleSampleRetrieved(retrieved: Boolean) {
        // nothing to do
    }

    private fun handleFailure(failure: Failure?) {
        _error.postValue(failure)
    }
}