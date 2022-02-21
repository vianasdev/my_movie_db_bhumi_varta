package com.example.my_movie_db.fragment.detail.datasource

import com.example.my_movie_db.contract.ApiContract
import com.example.my_movie_db.fragment.detail.model.CreditResponse
import com.example.my_movie_db.fragment.detail.model.DetailModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {
    @GET(ApiContract.GET_DETAIL)
    suspend fun getMovieDetail(
        @Path("movieId") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ):DetailModel

    @GET(ApiContract.GET_CREDIT)
    suspend fun getMovieCredit(
        @Path("movieId") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ):CreditResponse
}