package com.hderbali.usecase.usecases.post

import com.hderbali.model.Post
import com.hderbali.usecase.usecases.NoParamsFlowUseCase

interface GetTrendingPostsUseCase : NoParamsFlowUseCase<List<Post>>
