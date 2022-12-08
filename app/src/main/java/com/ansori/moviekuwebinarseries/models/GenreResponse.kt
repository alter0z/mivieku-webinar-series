package com.ansori.moviekuwebinarseries.models

import com.google.gson.annotations.SerializedName

data class GenreResponse(

	@field:SerializedName("genres")
	val genres: List<Genres>?
)