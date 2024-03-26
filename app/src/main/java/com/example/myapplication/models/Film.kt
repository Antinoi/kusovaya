package com.example.myapplication.models
import kotlinx.serialization.Serializable
@Serializable
data class Film(

    val id: Int,
    val poster: String,
    val title: String,
    val year: Int,
    val ageRating: Int,
    val movieLength: Int,
    val description: String

)
