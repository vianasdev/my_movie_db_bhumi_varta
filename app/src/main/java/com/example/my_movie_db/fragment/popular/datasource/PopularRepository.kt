package com.example.my_movie_db.fragment.popular.datasource

import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.utils.Result

interface PopularRepository {
    suspend fun getMovies(): Result<List<MovieModel>>
}