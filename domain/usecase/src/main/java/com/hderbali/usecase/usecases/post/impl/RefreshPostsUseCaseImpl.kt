package com.hderbali.usecase.usecases.post.impl

import com.hderbali.usecase.repository.PostRepository
import com.hderbali.ui.usescases.post.RefreshPostsUseCase
import javax.inject.Inject

class RefreshPostsUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : RefreshPostsUseCase {
    override suspend fun invoke() = postRepository.refreshPosts()
}
