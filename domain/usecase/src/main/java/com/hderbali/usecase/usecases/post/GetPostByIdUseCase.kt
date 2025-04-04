package com.hderbali.usecase.usecases.post

import com.hderbali.model.Post
import com.hderbali.usecase.usecases.FlowUseCase

interface GetPostByIdUseCase : FlowUseCase<String, Post>
