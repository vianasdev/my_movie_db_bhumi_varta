package com.example.my_movie_db.fragment.detail.datasource

import android.database.sqlite.SQLiteDatabase
import com.example.my_movie_db.databases.favorite.Favorite
import com.example.my_movie_db.databases.favorite.FavoriteRepository
import com.example.my_movie_db.fragment.detail.model.Cast
import com.example.my_movie_db.fragment.detail.model.DetailModel
import com.example.my_movie_db.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DetailDataSource constructor(
    private val api: DetailApi,
    private val db: SQLiteDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : DetailRepository {
    override suspend fun getMovieDetail(movieId: String): Result<DetailModel> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = api.getMovieDetail(
                    id = movieId,
                    apiKey = "4e017aafa0c4da4d663bc40fa6d6afe0",
                    language = "en-US"
                )
                Result.Success(result)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getMovieCredit(movieId: String): Result<List<Cast>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = api.getMovieCredit(
                    id = movieId,
                    apiKey = "4e017aafa0c4da4d663bc40fa6d6afe0",
                    language = "en-US"
                ).casts
                Result.Success(result)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }


    override suspend fun insertToFavorite(item: Favorite): Result<Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                val repository = FavoriteRepository()
                repository.insert(db, item)

                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getFavoriteMovies(): Result<List<Favorite>> = withContext(ioDispatcher) {
        return@withContext try {
            val repository = FavoriteRepository()
            val result = repository.select(db)

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun removeFromFavorite(item: DetailModel): Result<Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                val repository = FavoriteRepository()
                repository.deleteById(db, item.id)

                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}