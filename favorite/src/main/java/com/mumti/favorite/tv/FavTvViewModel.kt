package com.mumti.favorite.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mumti.mycore.domain.usecase.FilmUseCase

class FavTvViewModel(filmUseCase: FilmUseCase) : ViewModel() {
    val tv = filmUseCase.getFavoritedTvShows().asLiveData()
}