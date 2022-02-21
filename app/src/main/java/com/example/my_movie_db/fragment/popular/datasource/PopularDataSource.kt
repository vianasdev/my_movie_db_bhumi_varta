package com.example.my_movie_db.fragment.popular.datasource

import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PopularDataSource constructor(
    private val api: PopularApi,
    private val ioDispatcher: CoroutineDispatcher
) : PopularRepository {
    override suspend fun getMovies(): Result<List<MovieModel>> = withContext(ioDispatcher) {
        return@withContext try {
            val result = api.getMovies(
                apiKey = "4e017aafa0c4da4d663bc40fa6d6afe0",
                language = "en-US",
                sortBy = "popularity.desc",
                includeAdult = false,
                includeVideo = false,
                page = 1,
                year = "2021"
            ).results

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}