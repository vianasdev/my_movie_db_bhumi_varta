package com.example.my_movie_db.fragment.home.datasource

import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.utils.Result

interface HomeRepository {
    suspend fun getMovies(): Result<List<MovieModel>>

    suspend fun getCommingSoonMovies(): Result<List<MovieModel>>
}