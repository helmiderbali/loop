package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.model.Comment
import com.hderbali.model.CommentParams
import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.CommentRepository
import com.hderbali.usecase.usecases.comment.AddCommentUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddCommentUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : AddCommentUseCase {
    override suspend fun invoke(params: CommentParams): Flow<ResultOf<Comment>> =
        commentRepository.addComment(params.postId, params.content)
}
