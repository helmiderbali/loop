package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Comment
import com.hderbali.ui.usescases.comment.GetCommentsByPostIdUseCase
import com.hderbali.usecase.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentsByPostIdUseCaseImpl @Inject constructor(    private val commentRepository: CommentRepository
) : GetCommentsByPostIdUseCase {
    override fun invoke(params: String): Flow<ResultOf<List<Comment>>> =
        commentRepository.getCommentsByPostId(params)

}
