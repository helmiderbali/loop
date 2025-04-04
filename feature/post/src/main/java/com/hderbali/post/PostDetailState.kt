package com.hderbali.post

import com.hderbali.model.Comment
import com.hderbali.model.Post
import com.hderbali.model.Reply
import com.hderbali.model.User

data class PostDetailState(
    val isLoading: Boolean = true,
    val post: Post? = null,
    val postOwner: User? = null,
    val comments: List<CommentWithUser> = emptyList(),
    val error: String? = null,
    val isCommentSectionExpanded: Boolean = false,
    val newCommentText: String = "",
    val isSubmittingComment: Boolean = false
)

data class CommentWithUser(
    val comment: Comment,
    val user: User?,
    val repliesWithUsers: List<ReplyWithUser> = emptyList(),
    val isReplying: Boolean = false,
    val replyText: String = ""
)

data class ReplyWithUser(
    val reply: Reply,
    val user: User?
)
