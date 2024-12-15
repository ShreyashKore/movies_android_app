package dev.shreyash.androidmoviestask.domain

sealed interface Res<T> {
    class Loading<T>: Res<T>
    class Success<T>(val data: T) : Res<T>
    class Error<T>(val error: Throwable, val data: T? = null) : Res<T> {
        constructor(message: String) : this(Exception(message))
    }
}