package com.hderbali.usecase.usecases.post.impl

import com.hderbali.common.model.ResultOf
import com.hderbali.model.Post
import com.hderbali.ui.usescases.post.CreatePostUseCase
import com.hderbali.usecase.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreatePostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : CreatePostUseCase {

    override suspend fun invoke(post: Post): Flow<ResultOf<Post>> = flow {
        emit(ResultOf.Loading)

        try {
            validatePost(post)
            val savedPost = postRepository.createPost(post)
            emit(ResultOf.Success(savedPost))
        } catch (e: Exception) {
            emit(ResultOf.Error(e))
        }
    }

    private fun validatePost(post: Post) {
        if (post.content.isBlank() && post.media.isEmpty()) {
            throw IllegalArgumentException("Le post doit contenir au moins du texte ou une image")
        }

        if (post.userId.isBlank()) {
            throw IllegalArgumentException("L'ID utilisateur est requis")
        }

        if (post.tags.any { it.length > 30 }) {
            throw IllegalArgumentException("Les tags ne doivent pas dépasser 30 caractères")
        }

        if (post.content.length > 2000) {
            throw IllegalArgumentException("Le contenu ne doit pas dépasser 2000 caractères")
        }
    }
}