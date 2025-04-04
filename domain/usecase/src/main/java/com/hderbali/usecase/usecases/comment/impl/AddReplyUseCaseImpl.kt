package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.model.Reply
import com.hderbali.model.ReplyParams
import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.CommentRepository
import com.hderbali.usecase.usecases.comment.AddReplyUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddReplyUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : AddReplyUseCase {
    override suspend fun invoke(params: ReplyParams): Flow<ResultOf<Reply>> =
        commentRepository.addReply(params.commentId, params.content)
}
