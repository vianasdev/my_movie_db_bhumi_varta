package com.example.my_movie_db.databases.favorite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class FavoriteRepository {
    fun select(db: SQLiteDatabase): List<Favorite> {
        val selectQuery = String.format("SELECT * FROM %s", FavoriteContract.TABLE_NAME)
        val cursor = db.rawQuery(selectQuery, null)
        val items = cursorToPopular(cursor)
        cursor.close()

        return items
    }

    private fun cursorToPopular(cursor: Cursor): List<Favorite> {
        val items = arrayListOf<Favorite>()

        if (cursor.moveToFirst()) {
            val id = cursor.getColumnIndex(FavoriteContract.ID)
            val title = cursor.getColumnIndex(FavoriteContract.TITLE)
            val releaseDate = cursor.getColumnIndex(FavoriteContract.RELEASE_DATE)
            val backdropPath = cursor.getColumnIndex(FavoriteContract.BACKDROP_PATH)
            val genres = cursor.getColumnIndex(FavoriteContract.GENRES)

            do {
                val item = Favorite(
                    cursor.getString(id),
                    cursor.getString(title),
                    cursor.getString(releaseDate),
                    cursor.getString(genres),
                    cursor.getString(backdropPath)
                )
                items.add(item)
            } while (cursor.moveToNext())
        }
        return items
    }

    fun insert(db: SQLiteDatabase, item: Favorite) {
        val contentValues = favoriteToContentValues(item)
        db.insert(FavoriteContract.TABLE_NAME, null, contentValues)
    }

    private fun favoriteToContentValues(item: Favorite): ContentValues {
        val contentValues = ContentValues()
        contentValues.apply {
            put(FavoriteContract.ID, item.id)
            put(FavoriteContract.TITLE, item.title)
            put(FavoriteContract.RELEASE_DATE, item.releaseDate)
            put(FavoriteContract.BACKDROP_PATH, item.backdropPath)
            put(FavoriteContract.GENRES, item.genres)
        }
        return contentValues
    }

    fun deleteById(db: SQLiteDatabase, id: String) {
        val whereClause = String.format("%s = ?", FavoriteContract.ID)
        val whereArgs = arrayOf(id)
        db.delete(FavoriteContract.TABLE_NAME, whereClause, whereArgs)
    }


}