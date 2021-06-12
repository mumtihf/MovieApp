package com.mumti.mycore.utils

import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.mycore.data.local.entity.TvEntity
import com.mumti.mycore.data.remote.response.movie.MovieResponse
import com.mumti.mycore.data.remote.response.tv.TvResponse
import com.mumti.mycore.domain.model.Movie
import com.mumti.mycore.domain.model.TvShow

object DataMapper {
    //movies
    fun mapResponsesToMovieEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                    filmId = it.id,
                    title = it.title,
                    popularity = it.popularity,
                    voteAverage = it.voteAverage,
                    overview = it.overview,
                    releaseDate = it.releaseDate,
                    posterPath = "https://image.tmdb.org/t/p/original/${it.posterPath}"
            )
            movieList.add(movie)
        }
        return movieList
    }


    fun mapEntitiesToMovieDomain(input: List<MovieEntity>): List<Movie> =
            input.map {
                Movie(
                        filmId = it.filmId,
                        title = it.title,
                        popularity = it.popularity,
                        voteAverage = it.voteAverage,
                        overview = it.overview,
                        date = it.releaseDate,
                        imagePath = it.posterPath,
                        favorited = it.favorited
                )
            }

    fun mapEntitiesToDetailMovieDomain(input: MovieEntity): Movie =
            Movie(
                    filmId = input.filmId,
                    title = input.title,
                    popularity = input.popularity,
                    voteAverage = input.voteAverage,
                    overview = input.overview,
                    date = input.releaseDate,
                    imagePath = input.posterPath,
                    favorited = input.favorited
            )

    fun mapDomainToMovieEntity(input: Movie) = MovieEntity(
            filmId = input.filmId,
            title = input.title,
            popularity = input.popularity,
            voteAverage = input.voteAverage,
            overview = input.overview,
            releaseDate = input.date,
            posterPath = input.imagePath,
            favorited = input.favorited
    )

    //tv show
    fun mapResponsesToTvEntities(input: List<TvResponse>): List<TvEntity> {
        val tvList = ArrayList<TvEntity>()
        input.map {
            val tv = TvEntity(
                    filmId = it.id,
                    title = it.name,
                    popularity = it.popularity,
                    voteAverage = it.voteAverage,
                    overview = it.overview,
                    date = it.firstAirDate,
                    imagePath = "https://image.tmdb.org/t/p/original/${it.posterPath}",
                    favorited = false
            )
            tvList.add(tv)
        }
        return tvList
    }

    fun mapResponsesToDetailTvEntities(input: TvResponse): TvEntity {
        val tv = TvEntity(
                filmId = input.id,
                title = input.name,
                popularity = input.popularity,
                voteAverage = input.voteAverage,
                overview = input.overview,
                date = input.firstAirDate,
                imagePath = "https://image.tmdb.org/t/p/original/${input.posterPath}",
                favorited = false)
        return tv
    }

    fun mapEntitiesToTvDomain(input: List<TvEntity>): List<TvShow> =
            input.map {
                TvShow(
                        filmId = it.filmId,
                        title = it.title,
                        popularity = it.popularity,
                        voteAverage = it.voteAverage,
                        overview = it.overview,
                        date = it.date,
                        imagePath = it.imagePath,
                        favorited = it.favorited
                )
            }

    fun mapEntitiesToDetailTvDomain(input: TvEntity): TvShow =
            TvShow(
                    filmId = input.filmId,
                    title = input.title,
                    popularity = input.popularity,
                    voteAverage = input.voteAverage,
                    overview = input.overview,
                    date = input.date,
                    imagePath = input.imagePath,
                    favorited = input.favorited
            )

    fun mapDomainToTvEntity(input: TvShow) = TvEntity(
            filmId = input.filmId,
            title = input.title,
            popularity = input.popularity,
            voteAverage = input.voteAverage,
            overview = input.overview,
            date = input.date,
            imagePath = input.imagePath,
            favorited = input.favorited
    )
}