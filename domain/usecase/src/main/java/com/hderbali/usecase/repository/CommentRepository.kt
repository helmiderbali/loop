package com.hderbali.usecase.repository

import com.hderbali.model.Comment
import com.hderbali.model.ResultOf
import com.hderbali.model.Reply
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentsByPostId(postId: String): Flow<ResultOf<List<Comment>>>
    suspend fun addComment(postId: String, content: String): Flow<ResultOf<Comment>>
    suspend fun likeComment(commentId: String): Flow<ResultOf<Boolean>>
    suspend fun addReply(commentId: String, content: String): Flow<ResultOf<Reply>>
    suspend fun refreshComments()
}
