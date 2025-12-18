package com.ifba.meuifba.utils

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

// Extension para facilitar uso
fun <T> UiState<T>.isLoading() = this is UiState.Loading
fun <T> UiState<T>.isSuccess() = this is UiState.Success
fun <T> UiState<T>.isError() = this is UiState.Error
fun <T> UiState<T>.isIdle() = this is UiState.Idle

fun <T> UiState<T>.getDataOrNull(): T? {
    return if (this is UiState.Success) this.data else null
}

fun <T> UiState<T>.getErrorOrNull(): String? {
    return if (this is UiState.Error) this.message else null
}