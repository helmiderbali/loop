package com.hderbali.source_local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hderbali.source_local.db.entities.CommentEntity
import com.hderbali.source_local.db.entities.CommentWithReplies
import com.hderbali.source_local.db.entities.ReplyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Transaction
    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY timestamp ASC")
    fun observeCommentsByPostId(postId: String): Flow<List<CommentWithReplies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplies(replies: List<ReplyEntity>)

    @Update
    suspend fun updateComment(comment: CommentEntity)

    @Update
    suspend fun updateReply(reply: ReplyEntity)

    @Query("DELETE FROM comments")
    suspend fun deleteAllComments()

    @Query("DELETE FROM replies")
    suspend fun deleteAllReplies()
}
