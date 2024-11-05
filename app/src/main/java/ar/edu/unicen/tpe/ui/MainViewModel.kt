package ar.edu.unicen.tpe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.tpe.ddl.data.MovieRepository
import ar.edu.unicen.tpe.ddl.model.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _movies = MutableStateFlow<RemoteResult?>(null)
    val movies = _movies.asStateFlow()

    private val _page = MutableStateFlow(1)
    val page = _page.asStateFlow()

    fun loadData(
        page: Int = _page.value,
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _movies.value = null

            val movies = movieRepository.getMovies(page, Locale.getDefault().language)
            _loading.value = false
            _movies.value = movies
            _error.value = movies == null
        }
    }

    fun nextPage() {
        _page.value += 1
        loadData(_page.value)
    }

    fun prevPage() {
        if (_page.value > 1) {
            _page.value -= 1
            loadData(_page.value)
        }
    }

    fun goHome() {
        _page.value = 1
        loadData(_page.value)
    }
}