package com.leodemo.taipei_tour_core.data.retrofit

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Failure(val exception: Throwable) : ApiResponse<Nothing>()
}

fun <T> ApiResponse<T>.getOrNull(): T? {
    return if (this is ApiResponse.Success) {
        this.data
    } else {
        null
    }
}

inline fun <T> ApiResponse<T>.getOrDefault(
    crossinline block: () -> T
): T {
    return if (this is ApiResponse.Success) {
        this.data
    } else {
        block()
    }
}

inline fun <T> ApiResponse<T>.onSuccess(
    onResult: ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

suspend inline fun <T> ApiResponse<T>.suspendOnSuccess(
    crossinline onResult: suspend ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

inline fun <T> ApiResponse<T>.onFailure(
    crossinline onResult: ApiResponse.Failure.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Failure) {
        onResult(this)
    }
    return this
}