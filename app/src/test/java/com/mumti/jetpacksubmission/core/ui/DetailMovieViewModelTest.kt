package com.mumti.jetpacksubmission.core.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mumti.mycore.data.FilmRepository
import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.mycore.data.local.entity.TvEntity
import com.mumti.jetpacksubmission.detail.DetailFilmViewModel
import com.mumti.mycore.data.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var viewModel: DetailFilmViewModel
    private val dummyMovies = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovies.filmId
    private val dummyTvShows = DataDummy.generateDummyTvShows()[0]
    private val tvId = dummyTvShows.filmId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<TvEntity>>


    @Before
    fun setUp() {
        viewModel = DetailFilmViewModel(filmRepository)
        viewModel.setSelectedMovies(movieId)
        viewModel.setSelectedTvShows(tvId)
    }

    @Test
    fun setSelectedMovies() {
        viewModel.setSelectedMovies(movieId)

        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(DataDummy.generateDetailMovies(dummyMovies, true))

        `when`(filmRepository.getDetailMovies(movieId)).thenReturn(expected)

        viewModel.detailMovie.observeForever(movieObserver)
        verify(movieObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.detailMovie.value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun setSelectedTvShows() {
        viewModel.setSelectedTvShows(tvId)

        val expected = MutableLiveData<Resource<TvEntity>>()
        expected.value = Resource.success(DataDummy.generateDetailTvShows(dummyTvShows, true))

        `when`(filmRepository.getDetailTvShows(tvId)).thenReturn(expected)

        viewModel.detailTvShow.observeForever(tvObserver)
        verify(tvObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.detailTvShow.value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun setFavoritedMovie() {
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(DataDummy.generateDetailMovies(dummyMovies, false))

        `when`(filmRepository.getDetailMovies(movieId)).thenReturn(expected)

        viewModel.detailMovie = filmRepository.getDetailMovies(movieId)
        viewModel.setFavoritedMovie()

        verify(filmRepository).setFavoritedMovies(dummyMovies, true)
        verifyNoMoreInteractions(movieObserver)
    }

    @Test
    fun setFavoritedTvShow() {
        val expected = MutableLiveData<Resource<TvEntity>>()
        expected.value = Resource.success(DataDummy.generateDetailTvShows(dummyTvShows, false))

        `when`(filmRepository.getDetailTvShows(tvId)).thenReturn(expected)

        viewModel.detailTvShow = filmRepository.getDetailTvShows(tvId)
        viewModel.setFavoritedTvShow()

        verify(filmRepository).setFavoritedTvShows(dummyTvShows, true)
        verifyNoMoreInteractions(tvObserver)
    }
}