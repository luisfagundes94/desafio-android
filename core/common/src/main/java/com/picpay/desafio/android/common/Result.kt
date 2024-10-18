package com.picpay.desafio.android.common

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
}

fun <T> Result<T>.getOrThrow(): T {
    return when (this) {
        is Result.Success -> this.data
        is Result.Error -> throw this.exception
    }
}
