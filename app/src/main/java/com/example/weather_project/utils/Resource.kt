package com.example.weather_project.utils

data class Resource<out T>(val status: Status, val data: T?, val error: Error?) {
    companion object {
        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Error?): Resource<T> {
            return Resource(Status.ERROR, null, error)
        }
    }
}
