package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Comment
import com.hderbali.model.CommentParams
import com.hderbali.ui.usescases.comment.AddCommentUseCase
import com.hderbali.usecase.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddCommentUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : AddCommentUseCase {
    override suspend fun invoke(params: CommentParams): Flow<ResultOf<Comment>> =
        commentRepository.addComment(params.postId, params.content)
}
