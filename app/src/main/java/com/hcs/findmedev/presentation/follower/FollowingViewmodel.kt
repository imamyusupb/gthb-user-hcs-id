package com.hcs.findmedev.presentation.follower

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcs.findmedev.domain.model.GithubUser
import com.hcs.findmedev.domain.usecase.GetFollowingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val getFollowingUseCase: GetFollowingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username: String = savedStateHandle["username"] ?: ""

    private val _following = MutableStateFlow<List<GithubUser>>(emptyList())
    val following: StateFlow<List<GithubUser>> = _following

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
                _following.value = getFollowingUseCase(username)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
