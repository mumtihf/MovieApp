package com.mumti.mycore.data.local

import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.mycore.data.local.entity.TvEntity
import com.mumti.mycore.data.local.room.FilmDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mFilmDao: FilmDao) {

    //movies
    fun getAllMovies(): Flow<List<MovieEntity>> = mFilmDao.getMovies()

    fun getFavMovies(): Flow<List<MovieEntity>> = mFilmDao.getFavMovies()

    fun getDetailMovie(filmId: String): Flow<MovieEntity> =
        mFilmDao.getDetailMovie(filmId)

    suspend fun insertMovies(movies: List<MovieEntity>) = mFilmDao.insertMovies(movies)

    fun setFavoritedMovies(movies: MovieEntity, newState: Boolean) {
        movies.favorited = newState
        mFilmDao.updateMovies(movies)
    }

    //tv shows
    fun getAllTvShows(): Flow<List<TvEntity>> = mFilmDao.getTvShows()

    fun getFavTvShows(): Flow<List<TvEntity>> = mFilmDao.getFavTv()

    fun getDetailTvShow(tvId: String): Flow<TvEntity> =
        mFilmDao.getDetailTv(tvId)

    suspend fun insertTvShows(tv: List<TvEntity>) = mFilmDao.insertTvShows(tv)

    fun setFavoritedTvShow(tv: TvEntity, newState: Boolean) {
        tv.favorited = newState
        mFilmDao.updateTvShows(tv)
    }
}
