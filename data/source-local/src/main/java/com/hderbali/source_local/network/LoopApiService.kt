package com.hderbali.source_local.network

import com.hderbali.model.CommentDto
import com.hderbali.model.NotificationDto
import com.hderbali.model.PostDto
import com.hderbali.model.UserDto
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET

@OptIn(InternalSerializationApi::class)
interface LoopApiService {
    @GET("https://run.mocky.io/v3/a41204df-443c-4679-bd9d-6a41958c4934")
    suspend fun getUsers(): List<UserDto>

    @GET("https://run.mocky.io/v3/bf3a4980-45b4-4220-8a53-b00846a11658")
    suspend fun getPosts(): List<PostDto>

    @GET("https://run.mocky.io/v3/f2aa982d-32ca-42d2-a5ac-32a3e355472b")
    suspend fun getComments(): List<CommentDto>

    @GET("https://run.mocky.io/v3/cf1179ab-2172-49de-a26a-680c56603750")
    suspend fun getNotifications(): List<NotificationDto>
}
