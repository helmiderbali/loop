package com.hderbali.source_local.network

import com.hderbali.model.CommentDto
import com.hderbali.model.NotificationDto
import com.hderbali.model.PostDto
import com.hderbali.model.UserDto
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET

@OptIn(InternalSerializationApi::class)
interface LoopApiService {
    @GET("/v3/85f2fe54-a6c6-4f19-a12e-f46b0a27cc3e")
    suspend fun getUsers(): List<UserDto>

    @GET("v3/799add30-156d-4446-9025-3d7db3aa24d5")
    suspend fun getPosts(): List<PostDto>

    @GET("v3/2ff9a311-5414-4206-8243-719a1b27bb2e")
    suspend fun getComments(): List<CommentDto>

    @GET("v3/37006496-6072-4bfe-981c-b8f4225e6ebe")
    suspend fun getNotifications(): List<NotificationDto>
}
