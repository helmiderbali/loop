package com.hderbali.repository

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import com.hderbali.source_local.db.dao.PostDao
import com.hderbali.source_local.db.entities.EntityMappers
import com.hderbali.source_local.network.LoopApiService
import com.hderbali.usecase.repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val apiService: LoopApiService,
    private val postDao: PostDao,
    private val mappers: EntityMappers
) : PostRepository {

    override fun getPosts(): Flow<ResultOf<List<Post>>> = flow {
        emit(ResultOf.Loading)

        emitAll(postDao.observeAllPosts()
            .map { entities ->
                if (entities.isEmpty()) {
                    ResultOf.Loading
                } else {
                    ResultOf.Success(mappers.run { entities.toPostDomain() })
                }
            }
        )

        try {
            refreshPosts()
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override fun getTrendingPosts(): Flow<ResultOf<List<Post>>> = flow {
        emit(ResultOf.Loading)

        emitAll(postDao.observeTrendingPosts()
            .map { entities ->
                ResultOf.Success(mappers.run { entities.toPostDomain() })
            }
        )
    }

    override fun getPostById(postId: String): Flow<ResultOf<Post>> = flow {
        emit(ResultOf.Loading)

        postDao.observePostById(postId)
            .collect { entity ->
                if (entity == null) {
                    emit(ResultOf.Error(IOException("Post not found")))
                } else {
                    emit(ResultOf.Success(mappers.run { entity.toDomain() }))
                }
            }
    }

    override fun getPostsByUserId(userId: String): Flow<ResultOf<List<Post>>> = flow {
        emit(ResultOf.Loading)

        emitAll(postDao.observePostsByUserId(userId)
            .map { entities ->
                ResultOf.Success(mappers.run { entities.toPostDomain() })
            }
        )
    }

    override suspend fun refreshPosts() {
        try {
            val remotePosts = apiService.getPosts()
            val entities = mappers.run { remotePosts.toPostEntities() }
            postDao.insertPosts(entities)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun likePost(postId: String): Flow<ResultOf<Boolean>> = flow {
        emit(ResultOf.Loading)

        try {
            val postEntity = postDao.observePostById(postId).first()
            if (postEntity != null) {
                val updatedPost = postEntity.copy(
                    isLiked = !postEntity.isLiked,
                    likeCount = if (postEntity.isLiked) postEntity.likeCount - 1 else postEntity.likeCount + 1
                )
                postDao.updatePost(updatedPost)
                emit(ResultOf.Success(true))
            } else {
                emit(ResultOf.Error(IOException("Post not found")))
            }
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override suspend fun bookmarkPost(postId: String): Flow<ResultOf<Boolean>> = flow {
        emit(ResultOf.Loading)

        try {
            val postEntity = postDao.observePostById(postId).first()
            if (postEntity != null) {
                val updatedPost = postEntity.copy(
                    isBookmarked = !postEntity.isBookmarked
                )
                postDao.updatePost(updatedPost)
                emit(ResultOf.Success(true))
            } else {
                emit(ResultOf.Error(IOException("Post not found")))
            }
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    override suspend fun createPost(post: Post): Post {
        try {
            val finalPost = if (post.id.isBlank()) {
                post.copy(id = UUID.randomUUID().toString())
            } else {
                post
            }

            val postEntity = mappers.run { finalPost.toPostEntity() }
            postDao.insertPosts(listOf(postEntity))

            delay(500)

            return finalPost
        } catch (e: Exception) {
            throw e
        }
    }

}