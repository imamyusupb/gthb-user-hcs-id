package com.hcs.gitmeshow.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcs.core.model.UserFollower
import com.hcs.core.usecase.UserUseCase
import com.hcs.core.utils.state.LoaderState
import com.hcs.core.utils.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    /**
     * Loader state
     */
    private val _state: MutableLiveData<LoaderState>
        get() = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    /**
     * Error state
     */
    private val _error = MutableLiveData<String>()

    /**
     * Network error
     */
    private val _networkError = MutableLiveData<Boolean>()

    /**
     * State Followers
     */
    private val _resultUserFollower = MutableLiveData<List<UserFollower>>()
    val resultUserFollower: LiveData<List<UserFollower>>
        get() = _resultUserFollower

    fun getUserFollowers(username: String) {
        _state.value = LoaderState.ShowLoading
        viewModelScope.launch {
            userUseCase.getUserFollowers(username).collect {
                _state.value = LoaderState.HideLoading
                when (it) {
                    is ResultState.Success -> _resultUserFollower.postValue(it.data!!)
                    is ResultState.Error -> _error.postValue(it.error!!)
                    is ResultState.NetworkError -> _networkError.postValue(true)
                }
            }
        }
    }


}