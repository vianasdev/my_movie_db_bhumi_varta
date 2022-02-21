package com.example.my_movie_db.databases.favorite

import android.provider.BaseColumns

abstract class FavoriteContract : BaseColumns {
    companion object {
        const val TABLE_NAME = "popular"
        const val ID = "id"
        const val TITLE = "title"
        const val RELEASE_DATE = "releaseDate"
        const val BACKDROP_PATH = "backdropPath"
        const val GENRES = "genres"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID TEXT PRIMARY KEY, $TITLE TEXT, $RELEASE_DATE TEXT, $BACKDROP_PATH TEXT, $GENRES TEXT);"
    }
}