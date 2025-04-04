package com.hderbali.usecase.usecases.post

import com.hderbali.model.Post
import com.hderbali.usecase.usecases.FlowUseCase

interface GetPostsByUserIdUseCase : FlowUseCase<String, List<Post>>
