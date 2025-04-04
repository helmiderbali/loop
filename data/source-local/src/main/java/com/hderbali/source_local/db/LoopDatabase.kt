package com.hderbali.source_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hderbali.source_local.db.dao.CommentDao
import com.hderbali.source_local.db.dao.NotificationDao
import com.hderbali.source_local.db.dao.PostDao
import com.hderbali.source_local.db.dao.UserDao
import com.hderbali.source_local.db.entities.CommentEntity
import com.hderbali.source_local.db.entities.NotificationEntity
import com.hderbali.source_local.db.entities.PostEntity
import com.hderbali.source_local.db.entities.ReplyEntity
import com.hderbali.source_local.db.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PostEntity::class,
        CommentEntity::class,
        ReplyEntity::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SocialDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun notificationDao(): NotificationDao
}
