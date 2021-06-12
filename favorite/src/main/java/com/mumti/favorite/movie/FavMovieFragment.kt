package com.mumti.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mumti.jetpacksubmission.R
import com.mumti.mycore.domain.model.Movie
import com.mumti.jetpacksubmission.databinding.FragmentFavMovieBinding
import com.mumti.jetpacksubmission.detail.DetailFilmActivity
import com.mumti.mycore.ui.MovieAdapter
import com.mumti.jetpacksubmission.movie.MovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FavMovieFragment : Fragment(), MovieAdapter.MovieFragmentCallback {

    private var _binding: FragmentFavMovieBinding? = null
    private val favFragmentMovieBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavMovieBinding.inflate(layoutInflater, container, false)
        return favFragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val moviesAdapter = MovieAdapter(this)

            moviesAdapter.onItemClick = { selectedMovie ->
                val intent = Intent(activity, DetailFilmActivity::class.java)
                intent.putExtra(DetailFilmActivity.EXTRA_MOVIE, selectedMovie)
                startActivity(intent)
            }

            viewModel.favoriteMovie.observe(viewLifecycleOwner, { movies ->
                favFragmentMovieBinding.progressBar.visibility = View.VISIBLE
                if (movies != null) {
                    favFragmentMovieBinding.progressBar.visibility = View.GONE
                    moviesAdapter.setMovies(movies)
                    moviesAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show()
                }
            })

            with(favFragmentMovieBinding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
        }
    }

    override fun onShareClick(movies: Movie) {
        if (activity != null) {
            val mimeType = "plain/text"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan Movies sekarang.")
                .setText(resources.getString(R.string.share_text, movies.title))
                .startChooser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}