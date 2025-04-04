package com.hderbali.usecase.usecases.comment

import com.hderbali.model.Comment
import com.hderbali.model.CommentParams
import com.hderbali.usecase.usecases.SuspendFlowUseCase

interface AddCommentUseCase : SuspendFlowUseCase<CommentParams, Comment>
