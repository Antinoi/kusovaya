package com.example.myapplication.models.repositories

import com.example.myapplication.database.tables.Film
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getAllFilms(): Flow<List<Film>>
}