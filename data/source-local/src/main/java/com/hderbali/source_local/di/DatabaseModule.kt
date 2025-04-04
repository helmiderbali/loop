package com.hderbali.source_local.di

import android.content.Context
import androidx.room.Room
import com.hderbali.source_local.db.Converters
import com.hderbali.source_local.db.SocialDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSocialDatabase(@ApplicationContext context: Context): SocialDatabase {
        return Room.databaseBuilder(
            context,
            SocialDatabase::class.java,
            "social.db"
        ).build()
    }

    @Provides
    fun provideConverters(): Converters = Converters()

    @Provides
    fun provideUserDao(database: SocialDatabase) = database.userDao()

    @Provides
    fun providePostDao(database: SocialDatabase) = database.postDao()

    @Provides
    fun provideCommentDao(database: SocialDatabase) = database.commentDao()

    @Provides
    fun provideNotificationDao(database: SocialDatabase) = database.notificationDao()
}
