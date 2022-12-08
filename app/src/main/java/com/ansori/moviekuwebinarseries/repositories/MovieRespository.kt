package com.ansori.moviekuwebinarseries.repositories

import com.ansori.moviekuwebinarseries.BuildConfig
import com.ansori.moviekuwebinarseries.api.ApiConfig

class MovieRespository {
    private val client = ApiConfig.getApiService()

    suspend fun getPupularMovie(page: Int) = client.getPupularMovie(BuildConfig.API_KEY, page)
    suspend fun getMovieGenres() = client.getMovieGenres(BuildConfig.API_KEY)
}