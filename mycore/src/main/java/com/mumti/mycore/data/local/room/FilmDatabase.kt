package com.mumti.mycore.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mumti.mycore.data.local.entity.MovieEntity
import com.mumti.mycore.data.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class], version = 1, exportSchema = false)
abstract class FilmDatabase : RoomDatabase (){

    abstract fun filmDao(): FilmDao
}