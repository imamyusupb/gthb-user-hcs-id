package com.hcs.gitmeshow.following


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcs.core.model.UserFollowing
import com.hcs.core.usecase.UserUseCase
import com.hcs.core.utils.state.LoaderState
import com.hcs.core.utils.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    private val _resultUserFollowing = MutableLiveData<List<UserFollowing>>()
    val resultUserFollowing: LiveData<List<UserFollowing>>
        get() = _resultUserFollowing

    fun getUserFollowing(username: String) {
        _state.value = LoaderState.ShowLoading
        viewModelScope.launch {
            userUseCase.getUserFollowing(username).collect {
                _state.value = LoaderState.HideLoading
                if (it is ResultState.Success) _resultUserFollowing.postValue(it.data!!)
            }
        }
    }

}