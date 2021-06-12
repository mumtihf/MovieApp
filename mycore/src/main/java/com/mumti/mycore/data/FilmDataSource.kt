package com.mumti.mycore.data

import androidx.lifecycle.LiveData
import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.mycore.data.local.entity.TvEntity

interface FilmDataSource {

    fun getAllMovies(): LiveData<Resource<List<MovieEntity>>>

    fun getAllTvShows(): LiveData<Resource<List<TvEntity>>>

    fun getDetailMovies(filmId: String): LiveData<Resource<MovieEntity>>

    fun getDetailTvShows(filmId: String): LiveData<Resource<TvEntity>>

    fun getFavoritedMovies(): LiveData<List<MovieEntity>>

    fun getFavoritedTvShows(): LiveData<List<TvEntity>>

    fun setFavoritedMovies(movies: MovieEntity, state: Boolean)

    fun setFavoritedTvShows(tv: TvEntity, state: Boolean)
}