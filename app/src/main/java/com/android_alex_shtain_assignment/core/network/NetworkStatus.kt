package com.android_alex_shtain_assignment.core.network

sealed class NetworkStatus<out T> {

    object Loading : NetworkStatus<Nothing>()

    object NoInternet : NetworkStatus<Nothing>()

    class Error(val message: String) : NetworkStatus<Nothing>()

    class Success<T>(val data: T) : NetworkStatus<T>()

}
