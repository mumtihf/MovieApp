package com.mumti.jetpacksubmission.core.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mumti.mycore.data.local.LocalDataSource
import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.mycore.data.remote.ApiResponse
import com.mumti.mycore.data.remote.RemoteDataSource
import com.mumti.jetpacksubmission.core.data.remote.response.TvResponse
import com.mumti.mycore.utils.AppExecutors
import com.mumti.mycore.data.FilmDataSource
import com.mumti.mycore.data.NetworkBoundResource
import com.mumti.mycore.data.Resource

class FakeFilmRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
)
    : FilmDataSource {

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(movieResponses: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponses) {
                    val movie = MovieEntity(
                        response.filmId,
                        response.title,
                        response.type,
                        response.duration,
                        response.overview,
                        response.date,
                        response.slogan,
                        response.imagePath)
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<TvResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvResponse>>> =
                remoteDataSource.getAllTvShows()

            public override fun saveCallResult(tvResponses: List<TvResponse>) {
                val tvList = ArrayList<TvEntity>()
                for (response in tvResponses) {
                    val tv = TvEntity(
                        response.filmId,
                        response.title,
                        response.type,
                        response.duration,
                        response.overview,
                        response.date,
                        response.slogan,
                        response.imagePath)
                    tvList.add(tv)
                }
                localDataSource.insertTvShows(tvList)
            }
        }.asLiveData()
    }

    override fun getDetailMovies(filmId: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getDetailMovie(filmId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getDetailMovie(filmId)

            override fun saveCallResult(movieResponses: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponses) {
                    if (response.filmId == filmId) {
                        val movie = MovieEntity(
                            response.filmId,
                            response.title,
                            response.type,
                            response.duration,
                            response.overview,
                            response.date,
                            response.slogan,
                            response.imagePath)

                        movieList.add(movie)
                    }
                }
                localDataSource.getDetailMovie(filmId)
            }
        }.asLiveData()
    }

    override fun getDetailTvShows(filmId: String): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, List<TvResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<TvEntity> =
                localDataSource.getDetailTvShow(filmId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<List<TvResponse>>> =
                remoteDataSource.getDetailTvShow(filmId)

            override fun saveCallResult(tvResponses: List<TvResponse>) {
                val tvList = ArrayList<TvEntity>()
                for (response in tvResponses) {
                    if (response.filmId == filmId) {
                        val tv = TvEntity(
                            response.filmId,
                            response.title,
                            response.type,
                            response.duration,
                            response.overview,
                            response.date,
                            response.slogan,
                            response.imagePath)

                        tvList.add(tv)
                    }
                }
                localDataSource.getDetailTvShow(filmId)
            }
        }.asLiveData()
    }

    override fun getFavoritedMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavMovies(), config).build()
    }

    override fun getFavoritedTvShows(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavTvShows(), config).build()
    }

    override fun setFavoritedMovies(movies: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoritedMovies(movies, state) }

    override fun setFavoritedTvShows(tv: TvEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoritedTvShow(tv, state) }

}