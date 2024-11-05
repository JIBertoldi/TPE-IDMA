package ar.edu.unicen.tpe.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.tpe.ddl.data.MovieRepository
import ar.edu.unicen.tpe.ddl.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    fun getMovie(
        id: Int = -1,
        language:String ="en"
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _movie.value = null

            val movie = movieRepository.getMovie(id, language)
            _loading.value = false
            _movie.value = movie
            _error.value = movie == null
        }
    }

}