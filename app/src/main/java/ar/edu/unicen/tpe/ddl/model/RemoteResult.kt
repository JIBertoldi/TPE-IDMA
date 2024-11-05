package ar.edu.unicen.tpe.ddl.model

class RemoteResult(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
