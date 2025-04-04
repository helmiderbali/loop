package com.hderbali.ui.usescases.post

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import kotlinx.coroutines.flow.Flow

interface CreatePostUseCase {
    suspend operator fun invoke(post: Post): Flow<ResultOf<Post>>
}