package com.mumti.mycore.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mumti.mycore.domain.model.Movie
import com.mumti.mycore.R
import com.mumti.mycore.databinding.ItemsFilmBinding

class MovieAdapter(private val callback: MovieFragmentCallback) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var listMovies = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setMovies(movies: List<Movie>?) {
        if (movies == null) return
        this.listMovies.clear()
        this.listMovies.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsMovieBinding = ItemsFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = listMovies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    inner class MovieViewHolder(private val binding: ItemsFilmBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movie) {
            with(binding) {
                txtTitle.text = movies.title
                tvItemDate.text = movies.date
                /*
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilmActivity::class.java)
                    intent.putExtra(DetailFilmActivity.EXTRA_MOVIE, movies)
                    itemView.context.startActivity(intent)
                }

                 */
                imgShare.setOnClickListener { callback.onShareClick(movies) }
                Glide.with(itemView.context)
                    .load(movies.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imagePoster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listMovies[absoluteAdapterPosition])
            }
        }
    }

    interface MovieFragmentCallback {
        fun onShareClick(movies: Movie)
    }
}