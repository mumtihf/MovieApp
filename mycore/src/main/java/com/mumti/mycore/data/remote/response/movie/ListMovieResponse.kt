package com.mumti.mycore.data.remote.response.movie

import com.google.gson.annotations.SerializedName

data class ListMovieResponse(

        @field:SerializedName("results")
        val movies: List<MovieResponse>
)
