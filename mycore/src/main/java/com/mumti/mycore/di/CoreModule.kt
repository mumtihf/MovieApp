package com.mumti.mycore.di

import androidx.room.Room
import com.mumti.mycore.data.FilmRepository
import com.mumti.mycore.data.local.LocalDataSource
import com.mumti.mycore.data.local.room.FilmDatabase
import com.mumti.mycore.data.remote.RemoteDataSource
import com.mumti.mycore.data.remote.network.ApiService
import com.mumti.mycore.domain.repository.IFilmRepository
import com.mumti.mycore.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

    val databaseModule = module {
        factory { get<FilmDatabase>().filmDao() }
        single {
            Room.databaseBuilder(
                androidContext(),
                FilmDatabase::class.java, "Film.db"
            ).fallbackToDestructiveMigration().build()
        }
    }

    val networkModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<IFilmRepository> { FilmRepository(get(), get(), get()) }
    }