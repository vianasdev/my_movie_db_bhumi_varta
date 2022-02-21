package com.example.my_movie_db.contract

abstract class HttpContract {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"

        const val HTTP_CONNECT_TIMEOUT = 3 * 60
        const val HTTP_READ_TIMEOUT = 3 * 60
        const val HTTP_WRITE_TIMEOUT = 3 * 60
    }
}