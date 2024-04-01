package com.example.myapplication.database.views

import androidx.room.DatabaseView

@DatabaseView("SELECT likedfilms.likedFilmId AS likedFilmId, users.userId AS userId, films.filmId AS filmId, films.title AS title, " +
        "films.poster AS poster, films.year AS year, films.genre AS genre," +
        " films.description AS description FROM" +
        " films INNER JOIN likedFilms ON films.filmId = likedfilms.likedFilmId" +
        " INNER JOIN users ON users.userId = likedfilms.likedFilmId ")
data class UsersLikedFilms(
    val likedFilmId: Long,
    val filmId: Long,
    val userId: Long,

    val title: String,
    val poster: String,
    val year: String,
    val genre: String,
    val description: String
)
