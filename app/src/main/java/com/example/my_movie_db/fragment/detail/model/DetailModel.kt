package com.example.my_movie_db.fragment.detail.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailModel(
    @SerializedName("id")
    var id: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("runtime")
    var runtime: Int,
    @SerializedName("genres")
    var genres: List<Genre>,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("backdrop_path")
    var backdrop_path: String,
    @SerializedName("poster_path")
    var poster_path: String,
    @SerializedName("release_date")
    var release_date: String
) : Parcelable

@Parcelize
class Genre(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String
) : Parcelable

@Parcelize
data class CreditResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("cast")
    var casts: List<Cast>
) : Parcelable

@Parcelize
data class Cast(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("profile_path")
    var profile_path: String
) : Parcelable