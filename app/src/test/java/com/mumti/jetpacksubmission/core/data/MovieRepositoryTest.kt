package com.mumti.jetpacksubmission.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mumti.mycore.data.local.LocalDataSource
import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.mycore.data.local.room.FilmDao
import com.mumti.mycore.data.remote.RemoteDataSource
import com.mumti.mycore.utils.AppExecutors
import com.mumti.mycore.utils.LiveDataTestUtils
import com.mumti.jetpacksubmission.core.utils.PagedListUtil
import com.mumti.mycore.data.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val filmRepository = FakeFilmRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val moviesId = movieResponses[0].filmId
    private val tvResponses = DataDummy.generateRemoteDummyTvShow()
    private val tvId = tvResponses[0].filmId

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        filmRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        filmRepository.getAllTvShows()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(local).getAllTvShows()
        assertNotNull(tvEntities.data)
        assertEquals(tvResponses.size.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailMovies() {
        val dummyDetailMovieEntity = MutableLiveData<MovieEntity>()
        dummyDetailMovieEntity.value = DataDummy.generateDetailMovies(DataDummy.generateDummyMovies()[0], false)
        `when`(local.getDetailMovie(moviesId)).thenReturn(dummyDetailMovieEntity)

        val movieEntities = LiveDataTestUtils.getValue(filmRepository.getDetailMovies(moviesId))
        verify(local).getDetailMovie(eq(moviesId))

        assertNotNull(movieEntities.data)
        assertNotNull(movieEntities.data?.title)
        assertEquals(movieResponses[0].title, movieEntities.data?.title)
    }

    @Test
    fun getDetailTvShow() {
        val dummyDetailTvEntity = MutableLiveData<TvEntity>()
        dummyDetailTvEntity.value = DataDummy.generateDetailTvShows(DataDummy.generateDummyTvShows()[0], false)
        `when`(local.getDetailTvShow(tvId)).thenReturn(dummyDetailTvEntity)

        val tvEntities = LiveDataTestUtils.getValue(filmRepository.getDetailTvShows(tvId))
        verify(local).getDetailTvShow(tvId)

        assertNotNull(tvEntities.data)
        assertNotNull(tvEntities.data?.title)
        assertEquals(tvResponses[0].title, tvEntities.data?.title)
    }

    @Test
    fun getFavoritedMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavMovies()).thenReturn(dataSourceFactory)
        filmRepository.getFavoritedMovies()

        val favMovieEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getFavMovies()
        assertNotNull(favMovieEntity)
        assertEquals(movieResponses.size.toLong(), favMovieEntity.data?.size?.toLong())
    }

    @Test
    fun getFavoritedTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getFavTvShows()).thenReturn(dataSourceFactory)
        filmRepository.getFavoritedTvShows()

        val favTvShowEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(local).getFavTvShows()
        assertNotNull(favTvShowEntity)
        assertEquals(tvResponses.size.toLong(), favTvShowEntity.data?.size?.toLong())
    }

    @Test
    fun setFavoritedMovies() {
        val dao = spy(FilmDao::class.java)
        val localDataSource = LocalDataSource.getInstance(dao)
        val dummyMovie = DataDummy.generateDummyMovies()[0]
        val expected = dummyMovie.copy(favorited = true)
        doNothing().`when`(dao).updateMovies(expected)
        localDataSource.setFavoritedMovies(dummyMovie, true)
        verify(dao, times(1)).updateMovies(expected)
    }

    @Test
    fun setFavoritedTvShows() {
        val dao = spy(FilmDao::class.java)
        val localDataSource = LocalDataSource.getInstance(dao)
        val dummyTv = DataDummy.generateDummyTvShows()[0]
        val expected = dummyTv.copy(favorited = false)
        doNothing().`when`(dao).updateTvShows(expected)
        localDataSource.setFavoritedTvShow(dummyTv, false)
        verify(dao, times(1)).updateTvShows(expected)
    }
}