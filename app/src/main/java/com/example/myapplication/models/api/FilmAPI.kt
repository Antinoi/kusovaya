package com.example.myapplication.models.api
import com.example.myapplication.models.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface FilmAPI {
    @Headers("X-API-KEY: 47YA5WG-846458V-QCN3HVD-Y52Y97Q")

    @GET("https://api.kinopoisk.dev/v1.4/movie?ticketsOnSale=true&isSeries=false&country=\"Россия\"")
    suspend fun getAllFilms(): FilmResponse

}