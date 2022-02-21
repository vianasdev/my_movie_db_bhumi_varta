package com.example.my_movie_db.fragment.favorite.datasource

import com.example.my_movie_db.databases.favorite.Favorite
import com.example.my_movie_db.fragment.detail.model.DetailModel
import com.example.my_movie_db.utils.Result

interface FavoriteRepository {
    suspend fun getFavoriteMovies(): Result<List<Favorite>>

    suspend fun removeFromFavorite(item: Favorite): Result<Boolean>
}