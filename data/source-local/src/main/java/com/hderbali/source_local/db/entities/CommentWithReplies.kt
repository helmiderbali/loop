package com.hderbali.source_local.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CommentWithReplies(
    @Embedded val comment: CommentEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "commentId"
    )
    val replies: List<ReplyEntity>
)
