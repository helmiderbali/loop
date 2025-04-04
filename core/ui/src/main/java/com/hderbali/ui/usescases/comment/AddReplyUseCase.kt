package com.hderbali.ui.usescases.comment

import com.hderbali.model.Reply
import com.hderbali.model.ReplyParams
import com.hderbali.ui.usescases.SuspendFlowUseCase

interface AddReplyUseCase : SuspendFlowUseCase<ReplyParams, Reply>
