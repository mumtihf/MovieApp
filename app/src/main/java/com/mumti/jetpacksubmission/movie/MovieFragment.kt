package com.mumti.jetpacksubmission.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mumti.jetpacksubmission.R
import com.mumti.mycore.domain.model.Movie
import com.mumti.jetpacksubmission.databinding.FragmentMovieBinding
import com.mumti.jetpacksubmission.detail.DetailFilmActivity
import com.mumti.mycore.data.Resource
import com.mumti.mycore.ui.MovieAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(), MovieAdapter.MovieFragmentCallback {

    private var _binding: FragmentMovieBinding? = null
    private val fragmentMovieBinding get() = _binding!!

    private lateinit var moviesAdapter: MovieAdapter
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        if (activity != null) {
            moviesAdapter = MovieAdapter(this)

            moviesAdapter.onItemClick = { selectedMovie ->
                val intent = Intent(activity, DetailFilmActivity::class.java)
                intent.putExtra(DetailFilmActivity.EXTRA_MOVIE, selectedMovie)
                startActivity(intent)
            }

            viewModel.movies.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> fragmentMovieBinding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            fragmentMovieBinding.progressBar.visibility = View.GONE
                            moviesAdapter.setMovies(movies.data)
                            Log.d("ISI MOVIE DATA: ", movies.data.toString())
                            moviesAdapter.notifyDataSetChanged()
                        }
                        is Resource.Error -> {
                            fragmentMovieBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(fragmentMovieBinding.rvMovies) {
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

        with(_binding?.rvMovies) {
            if (this?.adapter != null) {
                this.adapter = null
            }
        }
        _binding = null
    }
}