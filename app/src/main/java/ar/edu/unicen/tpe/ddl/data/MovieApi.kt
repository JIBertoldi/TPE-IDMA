package ar.edu.unicen.tpe.ddl.data

import ar.edu.unicen.tpe.ddl.model.Movie
import ar.edu.unicen.tpe.ddl.model.RemoteResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<RemoteResult>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<Movie>
}