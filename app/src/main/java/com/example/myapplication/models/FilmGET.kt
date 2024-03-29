package com.example.myapplication.models

import com.example.myapplication.database.tables.Film
import kotlinx.serialization.Serializable



@Serializable
data class FilmResponse(
    val docs: List<FilmGET>
)

@Serializable
data class FilmGET(
    val id: Int,
    val countries: List<Country>,
    val genres: List<Genre>,
    val names: List<Name>,
    val alternativeName: String?,
    val description: String,
    val enName: String?,
    val movieLength: Int,
    val name: String,
    val poster: Poster,
    val rating: Rating,
    val ratingMpaa: String?,
    val shortDescription: String,
    val status: String?,
    val ticketsOnSale: Boolean,
    val type: String,
    val typeNumber: Int,
    val votes: Votes,
    val year: Int,
    val ageRating: Int,
    val backdrop: Backdrop,
    val logo: Logo?,
    val top10: String?,
    val top250: Int?,
    val isSeries: Boolean,
    val seriesLength: Int?,
    val totalSeriesLength: Int?
)

@Serializable
data class Country(
    val name: String
)

@Serializable
data class Genre(
    val name: String
)

@Serializable
data class Name(
    val name: String,
    val language: String? = null,
    val type: String? = null
)

@Serializable
data class Poster(
    val url: String,
    val previewUrl: String
)

@Serializable
data class Rating(
    val kp: Double?,
    val imdb: Double?,
    val filmCritics: Double?,
    val russianFilmCritics: Double?,
    val await: Double?
)

@Serializable
data class Votes(
    val kp: Int,
    val imdb: Int,
    val filmCritics: Int,
    val russianFilmCritics: Int,
    val await: Int
)

@Serializable
data class Backdrop(
    val url: String,
    val previewUrl: String
)

@Serializable
data class Logo(
    val url: String?
)


fun FilmResponse.toFilms(): List<Film> {
    return this.docs.map { filmGET ->
        val genres = if (filmGET.genres.isNotEmpty()) filmGET.genres[0].name else ""
        val countries = if (filmGET.countries.isNotEmpty()) filmGET.countries[0].name else ""
        Film(
            title = filmGET.name,
            year = filmGET.year.toLong(),
            ageRating = filmGET.ageRating,
            movieLength = filmGET.movieLength,
            description = filmGET.description,
            genre = genres,
            poster = filmGET.poster.url // Предполагаем, что URL постера хранится в объекте Poster
        )
    }
}