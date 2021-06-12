package com.mumti.jetpacksubmission.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mumti.mycore.domain.usecase.FilmUseCase

class TvViewModel(filmUseCase: FilmUseCase) : ViewModel() {

    val tvShow = filmUseCase.getAllTvShows().asLiveData()

    val favoriteTvShow = filmUseCase.getFavoritedTvShows().asLiveData()
}