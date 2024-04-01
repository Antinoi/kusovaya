package com.example.myapplication.models.repositories

import com.example.myapplication.models.filmsForApp
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getAllFilms(userId: Long): Flow<List<filmsForApp>>
}