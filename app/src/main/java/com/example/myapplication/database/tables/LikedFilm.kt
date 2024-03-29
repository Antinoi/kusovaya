package com.example.myapplication.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("likedFilms",
    foreignKeys = arrayOf(
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("idUser"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Film::class,
            parentColumns = arrayOf("filmId"),
            childColumns = arrayOf("idFilm"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)
    )
)
data class LikedFilm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("likedFilmId") val id: Long = 0,

    @ColumnInfo("idUser") val idUser: Long,
    @ColumnInfo("idFilm") val idFilm: Long

)
