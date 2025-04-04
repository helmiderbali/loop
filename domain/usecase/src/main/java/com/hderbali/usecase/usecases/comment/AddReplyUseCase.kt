package com.hderbali.usecase.usecases.comment

import com.hderbali.model.Reply
import com.hderbali.model.ReplyParams
import com.hderbali.usecase.usecases.SuspendFlowUseCase

interface AddReplyUseCase : SuspendFlowUseCase<ReplyParams, Reply>
