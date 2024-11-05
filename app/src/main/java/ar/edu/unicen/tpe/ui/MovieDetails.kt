package ar.edu.unicen.tpe.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.tpe.R
import ar.edu.unicen.tpe.databinding.ActivityMovieBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.floor

@AndroidEntryPoint
class MovieDetails : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private val viewModel by viewModels<MovieDetailsModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        val movieId = intent.getIntExtra("MOVIE_ID", -1)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getMovie(movieId, language = getString(R.string.language_api))
        }

        if (movieId != -1) {
            viewModel.getMovie(movieId, language = getString(R.string.language_api))
        } else {
            Toast.makeText(this, "Error: Movie ID is not valid", Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.onEach { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.dataMovie.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        viewModel.movie.onEach { movie ->
            if (movie != null) {
                binding.dataMovie.visibility = View.VISIBLE
                // Título
                binding.title.text = movie.title
                // Descripción
                binding.movieDescription.text = movie.overview.ifBlank {
                    getString(R.string.overview_404)
                }

                // Géneros
                val genresList = movie.genres.map { it.name }
                binding.genres.text = getString(R.string.genre) + ": " + when {
                    genresList.isEmpty() -> ""
                    genresList.size == 1 -> genresList[0]
                    genresList.size == 2 -> "${genresList[0]} y ${genresList[1]}"
                    else -> genresList.dropLast(1)
                        .joinToString(", ") + " ${getString(R.string.and)} ${genresList.last()}"
                }

                // Reseña
                binding.rating.text = getString(R.string.rating) + ": " +
                        (floor(movie.vote_average * 10) / 10).toString()

                // Portada
                Glide.with(binding.root.context)
                    .load("https://image.tmdb.org/t/p/w500/" + movie.backdrop_path)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_broken)
                    .into(binding.bgMovie)

                // Desactivar SwipeRefreshLayout
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }.launchIn(lifecycleScope)

        viewModel.error.onEach { error ->
            if (error) {
                binding.error.text = "Error de conexión"
                binding.error.visibility = View.VISIBLE
            } else {
                binding.error.visibility = View.INVISIBLE
            }
            // Desactivar SwipeRefreshLayout en caso de error
            binding.swipeRefreshLayout.isRefreshing = false
        }.launchIn(lifecycleScope)
    }
}
