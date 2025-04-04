package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.model.Comment
import com.hderbali.model.ResultOf
import com.hderbali.usecase.repository.CommentRepository
import com.hderbali.usecase.usecases.comment.GetCommentsByPostIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentsByPostIdUseCaseImpl @Inject constructor(    private val commentRepository: CommentRepository
) : GetCommentsByPostIdUseCase {
    override fun invoke(params: String): Flow<ResultOf<List<Comment>>> =
        commentRepository.getCommentsByPostId(params)

}
