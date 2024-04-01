package com.example.myapplication.database.tables
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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

data class FilmWithSeances(
    @Embedded val film: Film,
    @Relation(
        parentColumn = "filmId",
        entityColumn = "idFilm"
    )
    val seances: List<Seance>
)


data class FilmWithLikedFilms(
    @Embedded val film: Film,
    @Relation(
        parentColumn = "filmId",
        entityColumn = "idFilm"
    )
    val likedFilms: List<LikedFilm>
)


