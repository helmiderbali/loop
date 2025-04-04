package com.hderbali.ui.usescases.comment

import com.hderbali.model.Comment
import com.hderbali.model.CommentParams
import com.hderbali.ui.usescases.SuspendFlowUseCase

interface AddCommentUseCase : SuspendFlowUseCase<CommentParams, Comment>
