package com.example.ae2androidavanzado

import com.google.gson.annotations.SerializedName

data class MarvelResponse(
    @SerializedName("data") val data: Data
)

data class Data(
    @SerializedName("results") val results: List<Character>
)

data class Character(
    @SerializedName("name") val name: String,
    @SerializedName("thumbnail") val thumbnail: Thumbnail
)

data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun getFullImageUrl(): String {
        return path.replace("http://", "https://") + ".$extension"
    }
}

