package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.ui.usescases.comment.LikeCommentUseCase
import com.hderbali.usecase.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeCommentUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : LikeCommentUseCase {
    override suspend fun invoke(params: String): Flow<ResultOf<Boolean>> =
        commentRepository.likeComment(params)
}
