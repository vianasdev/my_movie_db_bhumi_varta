package com.example.my_movie_db.fragment.detail.datasource

import com.example.my_movie_db.databases.favorite.Favorite
import com.example.my_movie_db.fragment.detail.model.Cast
import com.example.my_movie_db.fragment.detail.model.DetailModel
import com.example.my_movie_db.utils.Result

interface DetailRepository {

    suspend fun getMovieDetail(movieId: String): Result<DetailModel>

    suspend fun getMovieCredit(movieId: String): Result<List<Cast>>

    suspend fun insertToFavorite(item: Favorite): Result<Boolean>

    suspend fun removeFromFavorite(item: DetailModel): Result<Boolean>

    suspend fun getFavoriteMovies(): Result<List<Favorite>>
}