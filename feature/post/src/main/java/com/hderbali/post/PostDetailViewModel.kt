package com.hderbali.post


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hderbali.common.model.ResultOf
import com.hderbali.model.CommentParams
import com.hderbali.model.ReplyParams
import com.hderbali.model.User
import com.hderbali.ui.usescases.comment.AddCommentUseCase
import com.hderbali.ui.usescases.comment.AddReplyUseCase
import com.hderbali.ui.usescases.comment.GetCommentsByPostIdUseCase
import com.hderbali.ui.usescases.comment.LikeCommentUseCase
import com.hderbali.ui.usescases.post.BookmarkPostUseCase
import com.hderbali.ui.usescases.post.GetPostByIdUseCase
import com.hderbali.ui.usescases.post.LikePostUseCase
import com.hderbali.ui.usescases.profile.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getCommentsByPostIdUseCase: GetCommentsByPostIdUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val bookmarkPostUseCase: BookmarkPostUseCase,
    private val likeCommentUseCase: LikeCommentUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val addReplyUseCase: AddReplyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PostDetailState())
    val state: StateFlow<PostDetailState> = _state.asStateFlow()

    private val userCache = mutableMapOf<String, User>()
    private val postId: String = checkNotNull(savedStateHandle["postId"])

    init {
        loadPostDetails()
    }

    private fun loadPostDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                launch { loadPost() }
                launch { loadComments() }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Une erreur s'est produite lors du chargement des détails."
                    )
                }
            }
        }
    }

    private suspend fun loadPost() {
        getPostByIdUseCase(postId)
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Erreur lors du chargement du post."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val post = result.data
                        val postOwner = getUserFromCache(post.userId)
                        _state.update {
                            it.copy(
                                post = post,
                                postOwner = postOwner,
                                isLoading = false
                            )
                        }
                    }
                    is ResultOf.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.exception.message ?: "Une erreur s'est produite."
                            )
                        }
                    }
                    ResultOf.Loading -> {
                    }
                }
            }
    }

    private suspend fun loadComments() {
        getCommentsByPostIdUseCase(postId)
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Erreur lors du chargement des commentaires."
                    )
                }
            }
            .collectLatest { result ->
                when (result) {
                    is ResultOf.Success -> {
                        val comments = result.data
                        val commentsWithUsers = comments.map { comment ->
                            val user = getUserFromCache(comment.userId)
                            val repliesWithUsers = comment.replies.map { reply ->
                                val replyUser = getUserFromCache(reply.userId)
                                ReplyWithUser(reply, replyUser)
                            }
                            CommentWithUser(comment, user, repliesWithUsers)
                        }
                        _state.update {
                            it.copy(
                                comments = commentsWithUsers,
                                isLoading = false
                            )
                        }
                    }
                    is ResultOf.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.exception.message ?: "Une erreur s'est produite."
                            )
                        }
                    }
                    ResultOf.Loading -> {
                    }
                }
            }
    }

    fun toggleCommentSection() {
        _state.update { it.copy(isCommentSectionExpanded = !it.isCommentSectionExpanded) }
    }

    fun updateNewCommentText(text: String) {
        _state.update { it.copy(newCommentText = text) }
    }

    fun submitComment() {
        val commentText = _state.value.newCommentText.trim()
        if (commentText.isEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(isSubmittingComment = true) }

            try {
                addCommentUseCase(CommentParams(postId, commentText)).collectLatest { result ->
                    when (result) {
                        is ResultOf.Success -> {
                            loadComments()
                            _state.update {
                                it.copy(
                                    newCommentText = "",
                                    isSubmittingComment = false
                                )
                            }
                        }
                        is ResultOf.Error -> {
                            _state.update {
                                it.copy(
                                    isSubmittingComment = false,
                                    error = result.exception.message ?: "Erreur lors de l'ajout du commentaire."
                                )
                            }
                        }
                        ResultOf.Loading -> {
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSubmittingComment = false,
                        error = e.message ?: "Erreur lors de l'ajout du commentaire."
                    )
                }
            }
        }
    }

    fun toggleReplyMode(commentId: String) {
        _state.update { state ->
            val updatedComments = state.comments.map { commentWithUser ->
                if (commentWithUser.comment.id == commentId) {
                    commentWithUser.copy(isReplying = !commentWithUser.isReplying)
                } else {
                    commentWithUser
                }
            }
            state.copy(comments = updatedComments)
        }
    }

    fun updateReplyText(commentId: String, text: String) {
        _state.update { state ->
            val updatedComments = state.comments.map { commentWithUser ->
                if (commentWithUser.comment.id == commentId) {
                    commentWithUser.copy(replyText = text)
                } else {
                    commentWithUser
                }
            }
            state.copy(comments = updatedComments)
        }
    }

    fun submitReply(commentId: String) {
        val commentWithUser = _state.value.comments.find { it.comment.id == commentId } ?: return
        val replyText = commentWithUser.replyText.trim()
        if (replyText.isEmpty()) return

        viewModelScope.launch {
            toggleReplyMode(commentId)

            try {
                addReplyUseCase(ReplyParams(commentId, replyText)).collectLatest { result ->
                    when (result) {
                        is ResultOf.Success -> {
                            loadComments()
                        }
                        is ResultOf.Error -> {
                            _state.update {
                                it.copy(
                                    error = result.exception.message ?: "Erreur lors de l'ajout de la réponse."
                                )
                            }
                        }
                        ResultOf.Loading -> {
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message ?: "Erreur lors de l'ajout de la réponse."
                    )
                }
            }
        }
    }

    fun likePost() {
        viewModelScope.launch {
            try {
                _state.value.post?.id?.let { postId ->
                    likePostUseCase(postId).collectLatest { result ->
                        if (result is ResultOf.Success && result.data) {
                            _state.update { state ->
                                val currentPost = state.post ?: return@update state
                                val updatedPost = currentPost.copy(
                                    isLiked = !currentPost.isLiked,
                                    likeCount = if (currentPost.isLiked) {
                                        currentPost.likeCount - 1
                                    } else {
                                        currentPost.likeCount + 1
                                    }
                                )
                                state.copy(post = updatedPost)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Impossible de liker ce post.") }
            }
        }
    }

    fun bookmarkPost() {
        viewModelScope.launch {
            try {
                _state.value.post?.id?.let { postId ->
                    bookmarkPostUseCase(postId).collectLatest { result ->
                        if (result is ResultOf.Success && result.data) {
                            _state.update { state ->
                                val currentPost = state.post ?: return@update state
                                val updatedPost = currentPost.copy(
                                    isBookmarked = !currentPost.isBookmarked
                                )
                                state.copy(post = updatedPost)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Impossible d'enregistrer ce post.") }
            }
        }
    }

    fun likeComment(commentId: String) {
        viewModelScope.launch {
            try {
                likeCommentUseCase(commentId).collectLatest { result ->
                    if (result is ResultOf.Success && result.data) {
                        _state.update { state ->
                            val updatedComments = state.comments.map { commentWithUser ->
                                if (commentWithUser.comment.id == commentId) {
                                    val updatedComment = commentWithUser.comment.copy(
                                        isLiked = !commentWithUser.comment.isLiked,
                                        likeCount = if (commentWithUser.comment.isLiked) {
                                            commentWithUser.comment.likeCount - 1
                                        } else {
                                            commentWithUser.comment.likeCount + 1
                                        }
                                    )
                                    commentWithUser.copy(comment = updatedComment)
                                } else {
                                    val updatedReplies = commentWithUser.repliesWithUsers.map { replyWithUser ->
                                        if (replyWithUser.reply.id == commentId) {
                                            val updatedReply = replyWithUser.reply.copy(
                                                isLiked = !replyWithUser.reply.isLiked,
                                                likeCount = if (replyWithUser.reply.isLiked) {
                                                    replyWithUser.reply.likeCount - 1
                                                } else {
                                                    replyWithUser.reply.likeCount + 1
                                                }
                                            )
                                            replyWithUser.copy(reply = updatedReply)
                                        } else {
                                            replyWithUser
                                        }
                                    }

                                    if (updatedReplies != commentWithUser.repliesWithUsers) {
                                        commentWithUser.copy(repliesWithUsers = updatedReplies)
                                    } else {
                                        commentWithUser
                                    }
                                }
                            }
                            state.copy(comments = updatedComments)
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Impossible de liker ce commentaire.") }
            }
        }
    }

    private suspend fun getUserFromCache(userId: String): User? {
        userCache[userId]?.let { return it }
        return try {
            val result = getUserByIdUseCase(userId)
                .first { it is ResultOf.Success || it is ResultOf.Error }
            if (result is ResultOf.Success) {
                val user = result.data
                userCache[userId] = user
                user
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}