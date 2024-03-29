package com.example.myapplication.database.tables
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("films")
data class Film(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("filmId") val id: Long = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("year") val year: Long,
    @ColumnInfo("ageRating") val ageRating: Int,
    @ColumnInfo("movieLength") val movieLength: Int,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("genre") val genre: String,
    @ColumnInfo("poster") val poster: String

)