package com.mumti.mycore.data.remote.network

import com.mumti.mycore.data.remote.response.movie.ListMovieResponse
import com.mumti.mycore.data.remote.response.tv.ListTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getMovies(
            @Query("api_key") api_key: String
    ): ListMovieResponse

    @GET("tv/popular")
    suspend fun getTvShows(
            @Query("api_key") api_key: String
    ): ListTvResponse
}