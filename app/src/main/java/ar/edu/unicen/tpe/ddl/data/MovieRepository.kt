package ar.edu.unicen.tpe.ddl.data

import ar.edu.unicen.tpe.ddl.model.Movie
import ar.edu.unicen.tpe.ddl.model.RemoteResult
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) {
    suspend fun getMovies(
        page: Int,
        language:String
    ): RemoteResult? {
        return movieRemoteDataSource.getMovies(page,language)
    }

    suspend fun getMovie(
        id: Int,
        language:String
    ): Movie? {
        return movieRemoteDataSource.getMovie(id,language)
    }

}