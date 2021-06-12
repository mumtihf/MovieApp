package com.mumti.mycore.data.remote

import android.util.Log
import com.mumti.mycore.data.remote.network.ApiService
import com.mumti.mycore.data.remote.response.movie.MovieResponse
import com.mumti.mycore.data.remote.response.tv.TvResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

    private val apiKey = "5c76f682aa428fda834db194b822f74c"

    suspend fun getAllMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovies(apiKey)
                val dataArray = response.movies
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.movies))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllTvShows(): Flow<ApiResponse<List<TvResponse>>> {
        return flow {
            try {
                val response = apiService.getTvShows(apiKey)
                val dataArray = response.tv
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.tv))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}