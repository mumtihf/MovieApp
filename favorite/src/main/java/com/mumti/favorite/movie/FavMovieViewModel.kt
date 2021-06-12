package com.mumti.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mumti.mycore.domain.usecase.FilmUseCase

class FavMovieViewModel(filmUseCase: FilmUseCase) : ViewModel() {
    val movie = filmUseCase.getFavoritedMovies().asLiveData()
}