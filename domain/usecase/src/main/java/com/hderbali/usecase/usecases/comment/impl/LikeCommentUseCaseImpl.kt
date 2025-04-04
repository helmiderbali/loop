package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.CommentRepository
import com.hderbali.usecase.usecases.comment.LikeCommentUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeCommentUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : LikeCommentUseCase {
    override suspend fun invoke(params: String): Flow<ResultOf<Boolean>> =
        commentRepository.likeComment(params)
}
