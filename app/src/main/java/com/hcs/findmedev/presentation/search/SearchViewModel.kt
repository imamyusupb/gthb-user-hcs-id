package com.hcs.findmedev.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcs.findmedev.domain.model.GithubUser
import com.hcs.findmedev.domain.usecase.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase
) : ViewModel() {

    private val _users = MutableStateFlow<List<GithubUser>>(emptyList())
    val users: StateFlow<List<GithubUser>> = _users

    fun search(query: String) {
        viewModelScope.launch {
            _users.value = searchUsersUseCase(query)
        }
    }
}
