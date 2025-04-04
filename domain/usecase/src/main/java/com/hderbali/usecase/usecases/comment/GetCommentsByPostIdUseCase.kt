package com.hderbali.usecase.usecases.comment

import com.hderbali.model.Comment
import com.hderbali.usecase.usecases.FlowUseCase

interface GetCommentsByPostIdUseCase : FlowUseCase<String, List<Comment>>
