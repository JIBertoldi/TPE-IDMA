package ar.edu.unicen.tpe.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.tpe.R
import ar.edu.unicen.tpe.databinding.MovieCardBinding
import ar.edu.unicen.tpe.ddl.model.Movie
import com.bumptech.glide.Glide
import kotlin.math.floor

class MovieAdapter(
    private val movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieCardBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(
        private val binding: MovieCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieName.text = movie.title

            val rate = floor(movie.vote_average * 10) / 10
            "$rate/10".also { binding.rating.text = it }


            val dateText = String.format(binding.root.context.getString(R.string.date))

            ("$dateText: ${movie.release_date}").also { binding.date.text = it }

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_broken)
                .into(binding.movieImage)

            binding.root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }
}