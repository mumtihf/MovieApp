package com.mumti.mycore.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tventities")
data class TvEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "filmId")
        var filmId: Int,

        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "popularity")
        var popularity: Double,

        @ColumnInfo(name = "vote_average")
        var voteAverage: Double,

        @ColumnInfo(name = "overview")
        var overview: String,

        @ColumnInfo(name = "date")
        var date: String,

        @ColumnInfo(name = "imagePath")
        var imagePath: String,

        @ColumnInfo(name = "favorited")
        var favorited: Boolean = false
)
