package com.example.my_movie_db.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("page")
    val page: String,
    @SerializedName("results")
    val results: List<MovieModel>
) : Parcelable