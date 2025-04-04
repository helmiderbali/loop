package com.hderbali.repository.di

import com.hderbali.repository.CommentRepositoryImpl
import com.hderbali.repository.NotificationRepositoryImpl
import com.hderbali.repository.PostRepositoryImpl
import com.hderbali.repository.UserRepositoryImpl
import com.hderbali.usecase.repository.CommentRepository
import com.hderbali.usecase.repository.NotificationRepository
import com.hderbali.usecase.repository.PostRepository
import com.hderbali.usecase.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository

    @Binds
    @Singleton
    abstract fun bindCommentRepository(
        commentRepositoryImpl: CommentRepositoryImpl
    ): CommentRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}