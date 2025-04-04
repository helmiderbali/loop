package com.hderbali.usecase.usecases.post.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.ui.usescases.post.BookmarkPostUseCase
import com.hderbali.usecase.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkPostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : BookmarkPostUseCase {
    override suspend fun invoke(params: String): Flow<ResultOf<Boolean>> = postRepository.bookmarkPost(params)
}
