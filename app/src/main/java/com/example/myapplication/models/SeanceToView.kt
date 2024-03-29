package com.example.myapplication.models

data class SeanceToView (
    val id : Long,
    val data: String,
    val time: String,
    val zal: String,
    val film: String,
    val poster: String,
    val idZal: Long,
    val idFilm: Long
)