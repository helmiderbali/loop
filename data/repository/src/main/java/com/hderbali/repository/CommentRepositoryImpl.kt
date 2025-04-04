package com.hderbali.repository

import com.hderbali.model.Comment
import com.hderbali.model.Reply
import com.hderbali.model.ResultOf
import com.hderbali.source_local.db.dao.CommentDao
import com.hderbali.source_local.db.entities.CommentEntity
import com.hderbali.source_local.db.entities.CommentWithReplies
import com.hderbali.source_local.db.entities.EntityMappers
import com.hderbali.source_local.db.entities.ReplyEntity
import com.hderbali.source_local.network.LoopApiService
import com.hderbali.usecase.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepositoryImpl @Inject constructor(
    private val apiService: LoopApiService,
    private val commentDao: CommentDao,
    private val mappers: EntityMappers
) : CommentRepository {

    override fun getCommentsByPostId(postId: String): Flow<ResultOf<List<Comment>>> = flow {
        emit(ResultOf.Loading)

        emitAll(commentDao.observeCommentsByPostId(postId)
            .map { entities ->
                ResultOf.Success(mappers.run { entities.toCommentDomain() })
            }
        )
    }

    override suspend fun addComment(postId: String, content: String): Flow<ResultOf<Comment>> = flow {
        emit(ResultOf.Loading)

        try {
            // In a real app, we would make an API call and then store the result
            // For this prototype, we'll create a mock comment
            val commentId = "comment_${System.currentTimeMillis()}"
            val newComment = CommentEntity(
                id = commentId,
                postId = postId,
                userId = "user10", // Current user
                content = content,
                timestamp = System.currentTimeMillis().toString(),
                likeCount = 0,
                isLiked = false
            )

            commentDao.insertComments(listOf(newComment))

            val commentWithReplies = CommentWithReplies(newComment, emptyList())
            emit(ResultOf.Success(mappers.run { commentWithReplies.toDomain() }))
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }
    override suspend fun likeComment(commentId: String): Flow<ResultOf<Boolean>> = flow {
        emit(ResultOf.Loading)

        try {
            // Find the comment
            val allComments = commentDao.observeCommentsByPostId("").first()
            val commentWithReplies = allComments.find { it.comment.id == commentId }

            if (commentWithReplies != null) {
                val updatedComment = commentWithReplies.comment.copy(
                    isLiked = !commentWithReplies.comment.isLiked,
                    likeCount = if (commentWithReplies.comment.isLiked) {
                        commentWithReplies.comment.likeCount - 1
                    } else {
                        commentWithReplies.comment.likeCount + 1
                    }
                )
                commentDao.updateComment(updatedComment)
                emit(ResultOf.Success(true))
            } else {
                // Check if it's a reply
                val reply = allComments.flatMap { it.replies }.find { it.id == commentId }
                if (reply != null) {
                    val updatedReply = reply.copy(
                        isLiked = !reply.isLiked,
                        likeCount = if (reply.isLiked) reply.likeCount - 1 else reply.likeCount + 1
                    )
                    commentDao.updateReply(updatedReply)
                    emit(ResultOf.Success(true))
                } else {
                    emit(ResultOf.Error(IOException("Comment not found")))
                }
            }
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override suspend fun addReply(commentId: String, content: String): Flow<ResultOf<Reply>> = flow {
        emit(ResultOf.Loading)

        try {
            // Mock reply creation
            val replyId = "reply_${System.currentTimeMillis()}"
            val newReply = ReplyEntity(
                id = replyId,
                commentId = commentId,
                userId = "user10", // Current user
                content = content,
                timestamp = System.currentTimeMillis().toString(),
                likeCount = 0,
                isLiked = false
            )

            commentDao.insertReplies(listOf(newReply))

            val domainReply = Reply(
                id = newReply.id,
                commentId = newReply.commentId,
                userId = newReply.userId,
                content = newReply.content,
                timestamp = newReply.timestamp,
                likeCount = newReply.likeCount,
                isLiked = newReply.isLiked
            )

            emit(ResultOf.Success(domainReply))
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override suspend fun refreshComments() {
        try {
            val remoteComments = apiService.getComments()
            val (commentEntities, replyEntities) = mappers.run { remoteComments.toCommentAndReplyEntities() }

            commentDao.deleteAllComments()
            commentDao.deleteAllReplies()

            commentDao.insertComments(commentEntities)
            commentDao.insertReplies(replyEntities)
        } catch (e: Exception) {
            throw e
        }
    }
}
