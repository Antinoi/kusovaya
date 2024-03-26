package com.example.myapplication.models.repositories

import com.example.myapplication.models.FilmResponse
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getAllFilms(): Flow<FilmResponse>
}