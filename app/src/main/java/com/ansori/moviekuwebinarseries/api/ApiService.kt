package com.ansori.moviekuwebinarseries.api

import com.ansori.moviekuwebinarseries.models.GenreResponse
import com.ansori.moviekuwebinarseries.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPupularMovie(@Query("api_key") key: String, @Query("page") page: Int): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(@Query("api_key") key: String): GenreResponse
}