package com.hcs.findmedev.domain.usecase

import com.hcs.findmedev.domain.repository.GithubRepository
import javax.inject.Inject

class GetFollowersUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String) = repository.getFollowers(username)
}
