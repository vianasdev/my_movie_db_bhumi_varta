package com.example.my_movie_db.databases.favorite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Favorite(
    var id: String,
    var title: String,
    var releaseDate: String,
    var genres: String,
    var backdropPath: String?
) : Parcelable