package com.hderbali.source_local.db.entities

import com.hderbali.model.Comment
import com.hderbali.model.CommentDto
import com.hderbali.model.Notification
import com.hderbali.model.NotificationDto
import com.hderbali.model.Post
import com.hderbali.model.PostDto
import com.hderbali.model.Reply
import com.hderbali.model.User
import com.hderbali.model.UserDto
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class EntityMappers @Inject constructor(private val json: Json) {
    fun UserDto.toEntity(): UserEntity = UserEntity(
        id = id,
        username = username,
        displayName = displayName,
        profilePicUrl = profilePicUrl,
        bio = bio,
        followerCount = followerCount,
        followingCount = followingCount,
        postCount = postCount,
        isVerified = isVerified,
        joinDate = joinDate
    )

    fun UserEntity.toDomain(): User = User(
        id = id,
        username = username,
        displayName = displayName,
        profilePicUrl = profilePicUrl,
        bio = bio,
        followerCount = followerCount,
        followingCount = followingCount,
        postCount = postCount,
        isVerified = isVerified,
        joinDate = joinDate
    )

    fun PostDto.toEntity(): PostEntity = PostEntity(
        id = id,
        userId = userId,
        type = type,
        content = content,
        media = media,
        timestamp = timestamp,
        likeCount = likeCount,
        commentCount = commentCount,
        viewCount = viewCount,
        location = location,
        tags = tags,
        isTrending = isTrending,
        isBookmarked = isBookmarked,
        isLiked = isLiked,
        spotifyLink = spotifyLink,
        link = link
    )

    fun PostEntity.toDomain(): Post = Post(
        id = id,
        userId = userId,
        type = type,
        content = content,
        media = media,
        timestamp = timestamp,
        likeCount = likeCount,
        commentCount = commentCount,
        viewCount = viewCount,
        location = location,
        tags = tags,
        isTrending = isTrending,
        isBookmarked = isBookmarked,
        isLiked = isLiked,
        spotifyLink = spotifyLink,
        link = link
    )

    fun CommentDto.toEntities(): Pair<CommentEntity, List<ReplyEntity>> {
        val commentEntity = CommentEntity(
            id = id,
            postId = postId,
            userId = userId,
            content = content,
            timestamp = timestamp,
            likeCount = likeCount,
            isLiked = isLiked
        )

        val replyEntities = replies.map { reply ->
            ReplyEntity(
                id = reply.id,
                commentId = id,
                userId = reply.userId,
                content = reply.content,
                timestamp = reply.timestamp,
                likeCount = reply.likeCount,
                isLiked = reply.isLiked
            )
        }

        return Pair(commentEntity, replyEntities)
    }

    fun CommentWithReplies.toDomain(): Comment = Comment(
        id = comment.id,
        postId = comment.postId,
        userId = comment.userId,
        content = comment.content,
        timestamp = comment.timestamp,
        likeCount = comment.likeCount,
        isLiked = comment.isLiked,
        replies = replies.map { reply ->
            Reply(
                id = reply.id,
                commentId = reply.commentId,
                userId = reply.userId,
                content = reply.content,
                timestamp = reply.timestamp,
                likeCount = reply.likeCount,
                isLiked = reply.isLiked
            )
        }
    )

    fun NotificationDto.toEntity(): NotificationEntity = NotificationEntity(
        id = id,
        userId = userId,
        type = type,
        actorId = actorId,
        contentId = contentId,
        commentId = commentId,
        contentPreview = contentPreview,
        timestamp = timestamp,
        isRead = isRead
    )

    fun NotificationEntity.toDomain(): Notification = Notification(
        id = id,
        userId = userId,
        type = type,
        actorId = actorId,
        contentId = contentId,
        commentId = commentId,
        contentPreview = contentPreview,
        timestamp = timestamp,
        isRead = isRead
    )

    fun Post.toPostEntity(): PostEntity {
        return PostEntity(
            id = id,
            userId = userId,
            type = type,
            content = content,
            media = media,
            timestamp = timestamp,
            likeCount = likeCount,
            commentCount = commentCount,
            viewCount = viewCount,
            location = location,
            tags = tags,
            isTrending = isTrending,
            isBookmarked = isBookmarked,
            isLiked = isLiked,
            spotifyLink = spotifyLink,
            link = link,
            lastUpdated = System.currentTimeMillis()
        )
    }

    fun List<UserDto>.toUserEntities() = map { it.toEntity() }
    fun List<UserEntity>.toUserDomain() = map { it.toDomain() }

    fun List<PostDto>.toPostEntities() = map { it.toEntity() }
    fun List<PostEntity>.toPostDomain() = map { it.toDomain() }

    fun List<NotificationDto>.toNotificationEntities() = map { it.toEntity() }
    fun List<NotificationEntity>.toNotificationDomain() = map { it.toDomain() }

    fun List<CommentDto>.toCommentAndReplyEntities(): Pair<List<CommentEntity>, List<ReplyEntity>> {
        val result = map { it.toEntities() }
        return Pair(
            result.map { it.first },
            result.flatMap { it.second }
        )
    }

    fun List<CommentWithReplies>.toCommentDomain() = map { it.toDomain() }

}
