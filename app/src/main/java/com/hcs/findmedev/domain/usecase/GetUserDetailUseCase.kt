package com.hcs.findmedev.domain.usecase

import com.hcs.findmedev.domain.model.GithubUserDetail
import com.hcs.findmedev.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String): GithubUserDetail {
        return repository.getUserDetail(username)
    }
}
