package com.hderbali.usecase.usecases.comment.impl

import com.hderbali.ui.usescases.comment.RefreshCommentsUseCase
import com.hderbali.usecase.repository.CommentRepository
import javax.inject.Inject

class RefreshCommentsUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : RefreshCommentsUseCase {
    override suspend fun invoke() = commentRepository.refreshComments()
}
