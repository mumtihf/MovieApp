package com.mumti.mycore.data.local.room

import androidx.room.*
import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.mycore.data.local.entity.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    //movies
    @Query("SELECT * FROM movieentities")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentities WHERE favorited = 1")
    fun getFavMovies(): Flow<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM movieentities WHERE filmId = :filmId")
    fun getDetailMovie(filmId: String): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovies(movies: MovieEntity)

    //tv show
    @Query("SELECT * FROM tventities")
    fun getTvShows(): Flow<List<TvEntity>>

    @Query("SELECT * FROM tventities WHERE favorited = 1")
    fun getFavTv(): Flow<List<TvEntity>>

    @Transaction
    @Query("SELECT * FROM tventities WHERE filmId = :filmId")
    fun getDetailTv(filmId: String): Flow<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tv: List<TvEntity>)

    @Update
    fun updateTvShows(tv: TvEntity)
}