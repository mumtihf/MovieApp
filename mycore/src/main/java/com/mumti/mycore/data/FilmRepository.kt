package com.mumti.mycore.data

import com.mumti.mycore.data.local.LocalDataSource
import com.mumti.mycore.data.remote.ApiResponse
import com.mumti.mycore.data.remote.RemoteDataSource
import com.mumti.mycore.data.remote.response.movie.MovieResponse
import com.mumti.mycore.data.remote.response.tv.TvResponse
import com.mumti.mycore.domain.model.Movie
import com.mumti.mycore.domain.model.TvShow
import com.mumti.mycore.domain.repository.IFilmRepository
import com.mumti.mycore.utils.AppExecutors
import com.mumti.mycore.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilmRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IFilmRepository
    {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            public override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map { DataMapper.mapEntitiesToMovieDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToMovieEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getAllTvShows(): Flow<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvResponse>>() {
            public override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getAllTvShows().map { DataMapper.mapEntitiesToTvDomain(it) }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvResponse>>> =
                remoteDataSource.getAllTvShows()

            override suspend fun saveCallResult(data: List<TvResponse>) {
                val tvList = DataMapper.mapResponsesToTvEntities(data)
                localDataSource.insertTvShows(tvList)
            }
        }.asFlow()
    }
/*
    override fun getDetailMovies(filmId: String): LiveData<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<Movie> {
                return Transformations.map(localDataSource.getDetailMovie(filmId)) {
                    DataMapper.mapEntitiesToDetailMovieDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(filmId)

            override fun saveCallResult(data: DetailMovieResponse) {
                val movieList = DataMapper.mapResponsesToDetailMovieEntities(data)
                localDataSource.insertDetailMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getDetailTvShows(filmId: String): LiveData<Resource<TvShow>> {
        return object : NetworkBoundResource<TvShow, DetailTvResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShow> {
                return Transformations.map(localDataSource.getDetailTvShow(filmId)) {
                    DataMapper.mapEntitiesToDetailTvDomain(it)
                }
            }

            override fun shouldFetch(data: TvShow?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<DetailTvResponse>> =
                remoteDataSource.getDetailTvShow(filmId)

            override fun saveCallResult(data: DetailTvResponse) {
                val tvList = DataMapper.mapResponsesToDetailTvEntities(data)
                localDataSource.insertDetailTvShows(tvList)
            }
        }.asLiveData()
    }
 */

    override fun getFavoritedMovies(): Flow<List<Movie>> {
        return localDataSource.getFavMovies().map { DataMapper.mapEntitiesToMovieDomain(it) }
    }

    override fun getFavoritedTvShows(): Flow<List<TvShow>> {
        return localDataSource.getFavTvShows().map { DataMapper.mapEntitiesToTvDomain(it) }
    }

    override fun setFavoritedMovies(movies: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToMovieEntity(movies)
        appExecutors.diskIO().execute { localDataSource.setFavoritedMovies(movieEntity, state) }
    }

    override fun setFavoritedTvShows(tv: TvShow, state: Boolean) {
        val tvEntity = DataMapper.mapDomainToTvEntity(tv)
        appExecutors.diskIO().execute { localDataSource.setFavoritedTvShow(tvEntity, state) }
    }
}