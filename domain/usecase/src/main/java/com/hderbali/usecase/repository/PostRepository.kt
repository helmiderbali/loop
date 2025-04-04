package com.hderbali.usecase.repository

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<ResultOf<List<Post>>>
    fun getTrendingPosts(): Flow<ResultOf<List<Post>>>
    fun getPostById(postId: String): Flow<ResultOf<Post>>
    fun getPostsByUserId(userId: String): Flow<ResultOf<List<Post>>>
    suspend fun refreshPosts()
    suspend fun likePost(postId: String): Flow<ResultOf<Boolean>>
    suspend fun bookmarkPost(postId: String): Flow<ResultOf<Boolean>>
    suspend fun createPost(post: Post): Post
}
