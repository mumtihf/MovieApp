package com.mumti.mycore.domain.usecase

import com.mumti.mycore.domain.model.Movie
import com.mumti.mycore.domain.model.TvShow
import com.mumti.mycore.domain.repository.IFilmRepository

class FilmInteractor(private val filmRepository: IFilmRepository) : FilmUseCase {

    //movie
    override fun getAllMovies() = filmRepository.getAllMovies()

    override fun getFavoritedMovies() = filmRepository.getFavoritedMovies()

    override fun setFavoritedMovies(movie: Movie, state: Boolean) =
            filmRepository.setFavoritedMovies(movie, state)


    //tv show
    override fun getAllTvShows() = filmRepository.getAllTvShows()

    override fun getFavoritedTvShows() = filmRepository.getFavoritedTvShows()

    override fun setFavoritedTvShows(tvShow: TvShow, state: Boolean)  =
            filmRepository.setFavoritedTvShows(tvShow, state)

}