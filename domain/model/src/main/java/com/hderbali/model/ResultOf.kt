package com.hderbali.model

sealed class ResultOf<out T> {
    data class Success<T>(val data: T) : ResultOf<T>()
    data class Error(val exception: Exception) : ResultOf<Nothing>()
    object Loading : ResultOf<Nothing>()
}
