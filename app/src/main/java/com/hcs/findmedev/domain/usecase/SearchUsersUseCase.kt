package com.hcs.findmedev.domain.usecase

import com.hcs.findmedev.domain.model.GithubUser
import com.hcs.findmedev.domain.repository.GithubRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(query: String): List<GithubUser> {
        return repository.searchUsers(query)
    }
}
