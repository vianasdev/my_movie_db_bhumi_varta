package com.example.my_movie_db.fragment.favorite.datasource

import android.database.sqlite.SQLiteDatabase
import com.example.my_movie_db.databases.favorite.Favorite
import com.example.my_movie_db.fragment.detail.model.DetailModel
import com.example.my_movie_db.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FavoriteDataSource constructor(
    private val db: SQLiteDatabase, private val ioDispatcher: CoroutineDispatcher
) : FavoriteRepository {
    override suspend fun getFavoriteMovies(): Result<List<Favorite>> = withContext(ioDispatcher) {
        return@withContext try {
            val repository = com.example.my_movie_db.databases.favorite.FavoriteRepository()
            val result = repository.select(db)

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun removeFromFavorite(item: Favorite): Result<Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                val repository = com.example.my_movie_db.databases.favorite.FavoriteRepository()
                repository.deleteById(db, item.id)

                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}