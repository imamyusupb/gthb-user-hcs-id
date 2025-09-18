package com.hcs.findmedev.domain.usecase

import com.hcs.findmedev.domain.repository.GithubRepository
import javax.inject.Inject


class GetFollowingUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String) = repository.getFollowing(username)
}
