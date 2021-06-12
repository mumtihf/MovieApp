package com.mumti.mycore.data.remote.response.tv

import com.google.gson.annotations.SerializedName

data class TvResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("popularity")
	val popularity: Double,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("poster_path")
	val posterPath: String
)
