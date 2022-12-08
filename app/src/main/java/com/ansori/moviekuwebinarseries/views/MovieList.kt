package com.ansori.moviekuwebinarseries.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.ansori.moviekuwebinarseries.adapters.MovieListAdapter
import com.ansori.moviekuwebinarseries.api.Resource
import com.ansori.moviekuwebinarseries.databinding.ActivityMovieListBinding
import com.ansori.moviekuwebinarseries.viewmodels.MovieViewModel

class MovieList : AppCompatActivity() {
    private var _binding: ActivityMovieListBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    private var layoutManager: LayoutManager? = null
    private var adapter: MovieListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieViewModel.getPupularMovie()
        observeGenres()
        obsserveAnyChange()
        setupView()
    }

    private fun obsserveAnyChange() {
        movieViewModel.popularMovieData.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        hideLoading()
                        adapter?.differ?.submitList(it.data?.results?.toList())
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeGenres() {
        movieViewModel.getMovieGenres().observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> it.data.genres?.let { data -> adapter?.setGenreList(data) }
                    is Resource.Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MovieListAdapter()
        binding.movieList.layoutManager = layoutManager
        binding.movieList.adapter = adapter

        binding.movieList.addOnScrollListener(scrollListener)
    }

    private val scrollListener = object: OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                movieViewModel.getPupularMovie()
            }
        }
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}