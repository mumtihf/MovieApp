package com.mumti.mycore.domain.usecase

import com.mumti.mycore.domain.model.Movie
import com.mumti.mycore.domain.model.TvShow
import com.mumti.mycore.data.Resource
import kotlinx.coroutines.flow.Flow

interface FilmUseCase {

    //movies
    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getFavoritedMovies(): Flow<List<Movie>>

    fun setFavoritedMovies(movie: Movie, state: Boolean)

    //tv show
    fun getAllTvShows(): Flow<Resource<List<TvShow>>>

    fun getFavoritedTvShows(): Flow<List<TvShow>>

    fun setFavoritedTvShows(tvShow: TvShow, state: Boolean)
}