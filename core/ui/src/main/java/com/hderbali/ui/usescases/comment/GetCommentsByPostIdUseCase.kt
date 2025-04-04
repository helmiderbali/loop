package com.hderbali.ui.usescases.comment

import com.hderbali.model.Comment
import com.hderbali.ui.usescases.FlowUseCase

interface GetCommentsByPostIdUseCase : FlowUseCase<String, List<Comment>>
