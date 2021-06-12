package com.mumti.jetpacksubmission.di

import com.mumti.mycore.domain.usecase.FilmInteractor
import com.mumti.mycore.domain.usecase.FilmUseCase
import com.mumti.jetpacksubmission.detail.DetailFilmViewModel
import com.mumti.jetpacksubmission.movie.MovieViewModel
import com.mumti.jetpacksubmission.tv.TvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val useCaseModule = module {
        factory<FilmUseCase> { FilmInteractor(get()) }
    }

    val viewModelModule = module {
        viewModel { MovieViewModel(get()) }
        viewModel { TvViewModel(get()) }
        viewModel { DetailFilmViewModel(get()) }
    }