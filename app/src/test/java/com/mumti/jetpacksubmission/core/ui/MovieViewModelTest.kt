package com.mumti.jetpacksubmission.core.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mumti.mycore.data.FilmRepository
import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.jetpacksubmission.movie.MovieViewModel
import com.mumti.mycore.data.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var favObserver: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(filmRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        Mockito.`when`(dummyMovies.data?.size).thenReturn(19)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        Mockito.`when`(filmRepository.getAllMovies()).thenReturn(movies)
        val moviesEntities = viewModel.getMovies().value?.data
        Mockito.verify(filmRepository).getAllMovies()
        assertNotNull(moviesEntities)
        assertEquals(19, moviesEntities?.size)

        viewModel.getMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getFavMovies() {
        val dummyFavMovies = pagedList
        Mockito.`when`(dummyFavMovies.size).thenReturn(19)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyFavMovies

        Mockito.`when`(filmRepository.getFavoritedMovies()).thenReturn(movies)
        val moviesEntities = viewModel.getFavMovies().value
        Mockito.verify(filmRepository).getFavoritedMovies()
        assertNotNull(moviesEntities)
        assertEquals(19, moviesEntities?.size)

        viewModel.getFavMovies().observeForever(favObserver)
        Mockito.verify(favObserver).onChanged(dummyFavMovies)
    }
}