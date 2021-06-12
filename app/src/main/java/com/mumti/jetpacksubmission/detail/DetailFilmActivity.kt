package com.mumti.jetpacksubmission.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mumti.jetpacksubmission.R
import com.mumti.mycore.domain.model.Movie
import com.mumti.mycore.domain.model.TvShow
import com.mumti.jetpacksubmission.databinding.ActivityDetailFilmBinding
import com.mumti.jetpacksubmission.databinding.ContentDetailFilmBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFilmActivity : AppCompatActivity() {

    private lateinit var activityDetailFilmBinding: ActivityDetailFilmBinding
    private lateinit var detailFilmBinding: ContentDetailFilmBinding

    private val viewModel: DetailFilmViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailFilmBinding = ActivityDetailFilmBinding.inflate(layoutInflater)
        detailFilmBinding = activityDetailFilmBinding.detailContent

        setContentView(activityDetailFilmBinding.root)

        setSupportActionBar(activityDetailFilmBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            val dataMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
            populateMovies(dataMovie)

            val dataTv = intent.getParcelableExtra<TvShow>(EXTRA_TV)
            populateTv(dataTv)

            Log.d("DATA MOVIE: ", dataMovie.toString())
            Log.d("DATA TV: ", dataTv.toString())
        }
    }

    private fun populateMovies(movies: Movie?) {
        movies?.let {
            detailFilmBinding.txtTitle.text = movies.title
            detailFilmBinding.txtPopularity.text = movies.popularity.toString()
            detailFilmBinding.txtOverview.text = movies.overview
            detailFilmBinding.txtRating.text = movies.voteAverage.toString()
            detailFilmBinding.txtDate.text = movies.date

            Glide.with(this)
                    .load(movies.imagePath)
                    .transform(RoundedCorners(20))
                    .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                    .error(R.drawable.ic_error))
                    .into(detailFilmBinding.imagePoster)

            var favorited = movies.favorited
            setFavoriteState(favorited)

            detailFilmBinding.fabFav.setOnClickListener {
                favorited = !favorited
                viewModel.setFavoritedMovie(movies, favorited)
                setFavoriteState(favorited)
            }
        }
    }

    private fun populateTv(tv: TvShow?) {
        tv?.let {
            detailFilmBinding.txtTitle.text = tv.title
            detailFilmBinding.txtPopularity.text = tv.popularity.toString()
            detailFilmBinding.txtOverview.text = tv.overview
            detailFilmBinding.txtRating.text = tv.voteAverage.toString()
            detailFilmBinding.txtDate.text = tv.date

            Glide.with(this)
                    .load(tv.imagePath)
                    .transform(RoundedCorners(20))
                    .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                    .error(R.drawable.ic_error))
                    .into(detailFilmBinding.imagePoster)

            var favorited = tv.favorited
            setFavoriteState(favorited)

            detailFilmBinding.fabFav.setOnClickListener {
                favorited = !favorited
                viewModel.setFavoritedTvShow(tv, favorited)
                setFavoriteState(favorited)
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            detailFilmBinding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorited))
        } else {
            detailFilmBinding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV = "extra_tv"
    }
}