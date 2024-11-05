package ar.edu.unicen.tpe.ddl.data

import ar.edu.unicen.tpe.ddl.model.Movie
import ar.edu.unicen.tpe.ddl.model.RemoteResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val apiKey = "c35d384d285d626baea6acb44287aaad"

class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi,
) {
    suspend fun getMovies(
        page: Int,
        language: String
    ): RemoteResult? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovies(apiKey, language, page)
                val movies = response.body()
                return@withContext movies
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getMovie(
        id: Int,
        language: String
    ): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovie(id,apiKey, language)
                val movie = response.body()
                return@withContext movie
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }
}