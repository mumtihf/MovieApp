package com.mumti.jetpacksubmission.core.ui

import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {
/*
    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Mock
    private lateinit var favObserver: Observer<PagedList<TvEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvEntity>

    @Before
    fun setUp() {
        viewModel = TvViewModel(filmRepository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShow = Resource.success(pagedList)
        Mockito.`when`(dummyTvShow.data?.size).thenReturn(17)
        val tv = MutableLiveData<Resource<List<TvEntity>>>()
        tv.value = dummyTvShow

        Mockito.`when`(filmRepository.getAllTvShows()).thenReturn(tv)
        val tvEntities = viewModel.getTvShows().value?.data
        verify(filmRepository).getAllTvShows()
        assertNotNull(tvEntities)
        assertEquals(17, tvEntities?.size)

        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }

    @Test
    fun getFavTvShows() {
        val dummyFavTvShows = pagedList
        Mockito.`when`(dummyFavTvShows.size).thenReturn(17)
        val tv = MutableLiveData<PagedList<MovieEntity>>()
        tv.value = dummyFavTvShows

        Mockito.`when`(filmRepository.getFavoritedTvShows()).thenReturn(tv)
        val tvEntities = viewModel.getFavTvShows().value
        Mockito.verify(filmRepository).getFavoritedTvShows()
        assertNotNull(tvEntities)
        assertEquals(17, tvEntities?.size)
        viewModel.getFavTvShows().observeForever(favObserver)
        Mockito.verify(favObserver).onChanged(dummyFavTvShows)
    }

 */
}