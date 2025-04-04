package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.usecase.repository.CommentRepository
import com.hderbali.usecase.usecases.comment.RefreshCommentsUseCase
import javax.inject.Inject

class RefreshCommentsUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : RefreshCommentsUseCase {
    override suspend fun invoke() = commentRepository.refreshComments()
}
