package ar.edu.unicen.tpe.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.tpe.R
import ar.edu.unicen.tpe.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar datos iniciales
        viewModel.loadData()

        binding.home.setOnClickListener() {
            viewModel.goHome()
        }

        // Swipe para recargar datos
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadData()
        }

        // Observa el estado de carga
        viewModel.loading.onEach { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
            if (!loading) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }.launchIn(lifecycleScope)

        // Observa la lista de películas y actualiza el adaptador
        viewModel.movies.onEach { movies ->
            binding.movieList.adapter = MovieAdapter(
                movies = movies?.results ?: emptyList(),
                onMovieClick = { movie ->
                    val intent = Intent(this, MovieDetails::class.java).apply {
                        putExtra("MOVIE_ID", movie.id)
                    }
                    startActivity(intent)
                }
            )
        }.launchIn(lifecycleScope)

        // Observa el estado de error
        viewModel.error.onEach { error ->
            if (error) {
                binding.error.text = "Error de conexión"
                binding.error.visibility = View.VISIBLE
            } else {
                binding.error.visibility = View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        // Acción para cargar la siguiente página de resultados
        binding.nextPageButton.setOnClickListener {
            viewModel.nextPage()
        }

        // Acción para cargar la siguiente página de resultados
        binding.prevPageButton.setOnClickListener {
            viewModel.prevPage()
        }

        viewModel.page.onEach { pageval ->
            if (pageval==1){
                binding.prevPageButton.visibility= View.INVISIBLE
            }else{
                binding.prevPageButton.visibility= View.VISIBLE
            }
            binding.pageNum.text =  pageval.toString()

        }.launchIn(lifecycleScope)
    }
}
