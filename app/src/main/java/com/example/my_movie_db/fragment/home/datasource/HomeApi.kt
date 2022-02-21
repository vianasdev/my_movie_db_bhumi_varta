package com.example.my_movie_db.fragment.home.datasource

import com.example.my_movie_db.contract.ApiContract
import com.example.my_movie_db.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET(ApiContract.GET_MOVIES)
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("page") page: Int
    ): MovieResponse

    @GET(ApiContract.GET_MOVIES)
    suspend fun getCommingSoonMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("page") page: Int,
        @Query("year") year: String
    ): MovieResponse
}