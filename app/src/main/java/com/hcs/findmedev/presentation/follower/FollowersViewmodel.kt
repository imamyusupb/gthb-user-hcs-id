package com.hcs.findmedev.presentation.follower

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcs.findmedev.domain.model.GithubUser
import com.hcs.findmedev.domain.usecase.GetFollowersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val getFollowersUseCase: GetFollowersUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username: String = savedStateHandle["username"] ?: ""

    private val _followers = MutableStateFlow<List<GithubUser>>(emptyList())
    val following: StateFlow<List<GithubUser>> = _followers

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadFollowing()
    }

    private fun loadFollowing() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _followers.value = getFollowersUseCase(username)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
