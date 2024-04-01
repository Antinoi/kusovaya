package com.example.myapplication.models

data class filmsForApp(
    val id: Long = 0,
    val title: String,
    val year: Long,
    val ageRating: Int,
    val movieLength: Int,
    val description: String,
    val genre: String,
    val poster: String,
    val liked: Boolean
)
