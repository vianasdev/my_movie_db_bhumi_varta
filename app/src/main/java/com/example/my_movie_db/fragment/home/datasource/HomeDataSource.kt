package com.example.my_movie_db.fragment.home.datasource

import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class HomeDataSource constructor(
    private val api: HomeApi,
    private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {
    override suspend fun getMovies(): Result<List<MovieModel>> = withContext(ioDispatcher) {
        return@withContext try {
            val result = api.getMovies(
                apiKey = "4e017aafa0c4da4d663bc40fa6d6afe0",
                language = "en-US",
                sortBy = "popularity.desc",
                includeAdult = false,
                includeVideo = false,
                page = 1
            ).results

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCommingSoonMovies(): Result<List<MovieModel>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val calendar = Calendar.getInstance()
                calendar.time = Date()
                calendar.add(Calendar.YEAR, 1)
                val nextYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(calendar.time)

                val result = api.getCommingSoonMovies(
                    apiKey = "4e017aafa0c4da4d663bc40fa6d6afe0",
                    language = "en-US",
                    sortBy = "popularity.desc",
                    includeAdult = false,
                    includeVideo = false,
                    page = 1,
                    year = nextYear
                ).results

                Result.Success(result)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}