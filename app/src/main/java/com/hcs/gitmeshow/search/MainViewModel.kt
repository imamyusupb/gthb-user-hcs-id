package com.hcs.gitmeshow.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcs.core.model.UserSearchItem
import com.hcs.core.usecase.UserUseCase
import com.hcs.core.utils.state.LoaderState
import com.hcs.core.utils.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {


    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    private val _error = MutableLiveData<String>()

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean>
        get() = _networkError

    private val _resultUserApi = MutableLiveData<List<UserSearchItem>>()
    val resultUserApi: LiveData<List<UserSearchItem>>
        get() = _resultUserApi

    fun getUserFromApi(query: String) {
        _state.value = LoaderState.ShowLoading
        viewModelScope.launch {
            userUseCase.getUserFromApi(query).collect {
                when (it) {

                    is ResultState.Success -> {
                        _resultUserApi.postValue(it.data!!)
                        _state.value = LoaderState.HideLoading
                    }

                    is ResultState.Error -> {
                        _error.postValue(it.error!!)
                    }

                    is ResultState.NetworkError -> {
                        _networkError.postValue(true)
                    }
                }
            }
        }
    }

}