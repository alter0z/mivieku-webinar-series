package com.ansori.moviekuwebinarseries.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ansori.moviekuwebinarseries.BuildConfig
import com.ansori.moviekuwebinarseries.databinding.MovieListBinding
import com.ansori.moviekuwebinarseries.models.Genres
import com.ansori.moviekuwebinarseries.models.Movies
import com.bumptech.glide.Glide

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private val genreList = ArrayList<Genres>()

    fun setGenreList(list: List<Genres>) {
        this.genreList.clear()
        this.genreList.addAll(list)
    }

    private val differCallback = object: DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(val binding: MovieListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(differ.currentList[position]) {
                Glide.with(itemView).load("${BuildConfig.PHOTO_BASE_URL}$posterPath").into(binding.poster)
                binding.title.text = originalTitle
                binding.lang.text = originalLanguage
                binding.releaseDate.text = releaseDate
                binding.ratingText.text = voteAverage.toString()
                binding.ratingBar.rating = voteAverage ?: 0f

                val map = genreList.associate { it.id to it.name }

                val genres = StringBuilder()
                val genresId = ArrayList<Int>()
                if (genreIds != null) {
                    genresId.addAll(genreIds)
                    for (data in genreIds) {
                        genres.append("${map[data]}, ")
                    }
                }

                binding.genre.text = genres.dropLast(2)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}