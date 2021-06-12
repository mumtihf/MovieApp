package com.mumti.favorite

import com.mumti.favorite.movie.FavMovieViewModel
import com.mumti.favorite.tv.FavTvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavMovieViewModel(get()) }
    viewModel { FavTvViewModel(get()) }
}