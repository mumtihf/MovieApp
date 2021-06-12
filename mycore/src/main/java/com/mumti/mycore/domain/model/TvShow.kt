package com.mumti.mycore.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
        val filmId: Int,
        val title: String,
        val popularity: Double,
        val voteAverage: Double,
        val overview: String,
        val date: String,
        val imagePath: String,
        val favorited: Boolean = false
): Parcelable
