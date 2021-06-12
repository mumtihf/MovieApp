package com.mumti.jetpacksubmission.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mumti.mycore.domain.model.Movie
import com.mumti.mycore.domain.model.TvShow
import com.mumti.mycore.domain.usecase.FilmUseCase

class DetailFilmViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {

    val filmId = MutableLiveData<String>()

    fun setSelectedMovies(moviesId: String) {
        this.filmId.value = moviesId
    }

    fun setSelectedTvShows(tvId: String) {
        this.filmId.value = tvId
    }

    fun setFavoritedMovie(movies: Movie, newStatus: Boolean) =
            filmUseCase.setFavoritedMovies(movies, newStatus)


    fun setFavoritedTvShow(tv: TvShow, newStatus: Boolean) =
            filmUseCase.setFavoritedTvShows(tv, newStatus)
}