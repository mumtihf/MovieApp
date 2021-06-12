package com.mumti.jetpacksubmission.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mumti.mycore.domain.usecase.FilmUseCase

class MovieViewModel(filmUseCase: FilmUseCase) : ViewModel() {

    val movies = filmUseCase.getAllMovies().asLiveData()

    val favoriteMovie = filmUseCase.getFavoritedMovies().asLiveData()
}