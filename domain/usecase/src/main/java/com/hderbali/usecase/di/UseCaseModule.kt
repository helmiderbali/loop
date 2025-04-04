package com.hderbali.usecase.di

import com.hderbali.ui.usescases.notification.GetNotificationsUseCase
import com.hderbali.ui.usescases.notification.GetUnreadNotificationsCountUseCase
import com.hderbali.ui.usescases.notification.MarkAllNotificationsAsReadUseCase
import com.hderbali.ui.usescases.notification.MarkNotificationAsReadUseCase
import com.hderbali.ui.usescases.notification.RefreshNotificationsUseCase
import com.hderbali.ui.usescases.comment.AddCommentUseCase
import com.hderbali.ui.usescases.comment.AddReplyUseCase
import com.hderbali.ui.usescases.comment.GetCommentsByPostIdUseCase
import com.hderbali.ui.usescases.comment.LikeCommentUseCase
import com.hderbali.ui.usescases.comment.RefreshCommentsUseCase
import com.hderbali.usecase.usecases.comment.impl.AddCommentUseCaseImpl
import com.hderbali.usecase.usecases.comment.impl.AddReplyUseCaseImpl
import com.hderbali.usecase.usecases.comment.impl.GetCommentsByPostIdUseCaseImpl
import com.hderbali.usecase.usecases.comment.impl.LikeCommentUseCaseImpl
import com.hderbali.usecase.usecases.comment.impl.RefreshCommentsUseCaseImpl
import com.hderbali.usecase.usecases.notification.impl.GetNotificationsUseCaseImpl
import com.hderbali.usecase.usecases.notification.impl.GetUnreadNotificationsCountUseCaseImpl
import com.hderbali.usecase.usecases.notification.impl.MarkAllNotificationsAsReadUseCaseImpl
import com.hderbali.usecase.usecases.notification.impl.MarkNotificationAsReadUseCaseImpl
import com.hderbali.usecase.usecases.notification.impl.RefreshNotificationsUseCaseImpl
import com.hderbali.ui.usescases.post.BookmarkPostUseCase
import com.hderbali.ui.usescases.post.CreatePostUseCase
import com.hderbali.ui.usescases.post.GetPostByIdUseCase
import com.hderbali.ui.usescases.post.GetPostsByUserIdUseCase
import com.hderbali.ui.usescases.post.GetPostsUseCase
import com.hderbali.ui.usescases.post.GetTrendingPostsUseCase
import com.hderbali.ui.usescases.post.LikePostUseCase
import com.hderbali.ui.usescases.post.RefreshPostsUseCase
import com.hderbali.ui.usescases.profile.GetCurrentUserUseCase
import com.hderbali.ui.usescases.profile.GetUserByIdUseCase
import com.hderbali.ui.usescases.profile.GetUsersUseCase
import com.hderbali.ui.usescases.profile.RefreshUsersUseCase
import com.hderbali.usecase.usecases.post.impl.BookmarkPostUseCaseImpl
import com.hderbali.usecase.usecases.post.impl.CreatePostUseCaseImpl
import com.hderbali.usecase.usecases.post.impl.GetPostByIdUseCaseImpl
import com.hderbali.usecase.usecases.post.impl.GetPostsByUserIdUseCaseImpl
import com.hderbali.usecase.usecases.post.impl.GetPostsUseCaseImpl
import com.hderbali.usecase.usecases.post.impl.GetTrendingPostsUseCaseImpl
import com.hderbali.usecase.usecases.post.impl.LikePostUseCaseImpl
import com.hderbali.usecase.usecases.post.impl.RefreshPostsUseCaseImpl
import com.hderbali.usecase.usecases.user.impl.GetCurrentUserUseCaseImpl
import com.hderbali.usecase.usecases.user.impl.GetUserByIdUseCaseImpl
import com.hderbali.usecase.usecases.user.impl.GetUsersUseCaseImpl
import com.hderbali.usecase.usecases.user.impl.RefreshUsersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetUsersUseCase(impl: GetUsersUseCaseImpl): GetUsersUseCase

    @Binds
    @Singleton
    abstract fun bindGetUserByIdUseCase(impl: GetUserByIdUseCaseImpl): GetUserByIdUseCase

    @Binds
    @Singleton
    abstract fun bindGetCurrentUserUseCase(impl: GetCurrentUserUseCaseImpl): GetCurrentUserUseCase

    @Binds
    @Singleton
    abstract fun bindRefreshUsersUseCase(impl: RefreshUsersUseCaseImpl): RefreshUsersUseCase

    @Binds
    @Singleton
    abstract fun bindGetPostsUseCase(impl: GetPostsUseCaseImpl): GetPostsUseCase

    @Binds
    @Singleton
    abstract fun bindGetTrendingPostsUseCase(impl: GetTrendingPostsUseCaseImpl): GetTrendingPostsUseCase

    @Binds
    @Singleton
    abstract fun bindGetPostByIdUseCase(impl: GetPostByIdUseCaseImpl): GetPostByIdUseCase

    @Binds
    @Singleton
    abstract fun bindGetPostsByUserIdUseCase(impl: GetPostsByUserIdUseCaseImpl): GetPostsByUserIdUseCase

    @Binds
    @Singleton
    abstract fun bindRefreshPostsUseCase(impl: RefreshPostsUseCaseImpl): RefreshPostsUseCase

    @Binds
    @Singleton
    abstract fun bindLikePostUseCase(impl: LikePostUseCaseImpl): LikePostUseCase

    @Binds
    @Singleton
    abstract fun bindBookmarkPostUseCase(impl: BookmarkPostUseCaseImpl): BookmarkPostUseCase

    // Comment Use Cases
    @Binds
    @Singleton
    abstract fun bindGetCommentsByPostIdUseCase(impl: GetCommentsByPostIdUseCaseImpl): GetCommentsByPostIdUseCase

    @Binds
    @Singleton
    abstract fun bindAddCommentUseCase(impl: AddCommentUseCaseImpl): AddCommentUseCase

    @Binds
    @Singleton
    abstract fun bindLikeCommentUseCase(impl: LikeCommentUseCaseImpl): LikeCommentUseCase

    @Binds
    @Singleton
    abstract fun bindAddReplyUseCase(impl: AddReplyUseCaseImpl): AddReplyUseCase

    @Binds
    @Singleton
    abstract fun bindRefreshCommentsUseCase(impl: RefreshCommentsUseCaseImpl): RefreshCommentsUseCase

    @Binds
    @Singleton
    abstract fun bindGetNotificationsUseCase(impl: GetNotificationsUseCaseImpl): GetNotificationsUseCase

    @Binds
    @Singleton
    abstract fun bindGetUnreadNotificationsCountUseCase(impl: GetUnreadNotificationsCountUseCaseImpl): GetUnreadNotificationsCountUseCase

    @Binds
    @Singleton
    abstract fun bindMarkNotificationAsReadUseCase(impl: MarkNotificationAsReadUseCaseImpl): MarkNotificationAsReadUseCase

    @Binds
    @Singleton
    abstract fun bindMarkAllNotificationsAsReadUseCase(impl: MarkAllNotificationsAsReadUseCaseImpl): MarkAllNotificationsAsReadUseCase

    @Binds
    @Singleton
    abstract fun bindRefreshNotificationsUseCase(impl: RefreshNotificationsUseCaseImpl): RefreshNotificationsUseCase

    @Binds
    @Singleton
    abstract fun bindCreatePostUseCase(impl: CreatePostUseCaseImpl): CreatePostUseCase
}