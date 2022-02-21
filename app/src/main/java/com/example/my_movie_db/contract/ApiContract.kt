package com.example.my_movie_db.contract

abstract class ApiContract {
    companion object {
        const val GET_MOVIES = "/3/discover/movie"
        const val GET_DETAIL = "/3/movie/{movieId}"
        const val GET_CREDIT = "/3/movie/{movieId}/credits"
    }
}