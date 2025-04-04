package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Reply
import com.hderbali.model.ReplyParams
import com.hderbali.ui.usescases.comment.AddReplyUseCase
import com.hderbali.usecase.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddReplyUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : AddReplyUseCase {
    override suspend fun invoke(params: ReplyParams): Flow<ResultOf<Reply>> =
        commentRepository.addReply(params.commentId, params.content)
}
