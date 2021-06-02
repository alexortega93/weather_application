package com.example.codingchallenge

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class OperationResult<out R> {

    object Starting: OperationResult<Nothing>()
    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Error(val exception: String) : OperationResult<Nothing>()
    object Loading : OperationResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Starting -> "Starting"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}
