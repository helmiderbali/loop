package com.hderbali.usecase.usecases.post.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import com.hderbali.usecase.repository.PostRepository
import com.hderbali.ui.usescases.post.GetTrendingPostsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingPostsUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetTrendingPostsUseCase {
    override fun invoke(): Flow<ResultOf<List<Post>>> = postRepository.getTrendingPosts()
}
