package com.ansori.moviekuwebinarseries.viewmodels

import androidx.lifecycle.*
import com.ansori.moviekuwebinarseries.api.Resource
import com.ansori.moviekuwebinarseries.models.GenreResponse
import com.ansori.moviekuwebinarseries.models.MovieResponse
import com.ansori.moviekuwebinarseries.repositories.MovieRespository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class MovieViewModel: ViewModel() {
    private val repository: MovieRespository = MovieRespository()
    private var popularMoviePage = 1
    private var popularMovieResponse: MovieResponse? = null
    private var _popularMovieData = MutableLiveData<Resource<MovieResponse?>>()
    val popularMovieData: LiveData<Resource<MovieResponse?>> = _popularMovieData

    fun getPupularMovie() {
        viewModelScope.launch {
            _popularMovieData.postValue(Resource.Loading)
            val response = repository.getPupularMovie(popularMoviePage)
            _popularMovieData.postValue(handlePopularMovieResponse(response))
        }
    }

    private fun handlePopularMovieResponse(response: Response<MovieResponse>): Resource<MovieResponse?> {
        return if (response.isSuccessful) {
            response.body()?.let {
                popularMoviePage++
                if (popularMovieResponse == null) popularMovieResponse = it else {
                    val oldMovies = popularMovieResponse?.results
                    val newMovies = it.results
                    if (newMovies != null) {
                        oldMovies?.addAll(newMovies)
                    }
                }
            }

            Resource.Success(popularMovieResponse ?: response.body())
        } else {
            Resource.Error(response.errorBody()?.string().toString())
        }
    }

    fun getMovieGenres(): LiveData<Resource<GenreResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = repository.getMovieGenres()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.response()?.errorBody()?.string().toString()))
        }
    }
}