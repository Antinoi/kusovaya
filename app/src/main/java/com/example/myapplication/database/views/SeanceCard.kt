package com.example.myapplication.database.views

import androidx.room.DatabaseView


@DatabaseView("SELECT films.filmId AS filmId, films.title AS title, films.poster AS poster, " +
        "seances.seanceId AS seanceId, seances.time AS time, seances.data AS data," +
        " zals.zalId AS zalId, zals.name AS zalName FROM" +
        " films INNER JOIN seances ON films.filmId = seances.idFilm" +
        " INNER JOIN zals ON zals.zalId = seances.idZal ")
data class SeanceCard(
    val seanceId: Long,
    val filmId: Long,
    val zalId: Long,

    val title: String,
    val poster: String,

    val zalName: String,

    val time: String,
    val data: String
)
