package com.example.myapplication.models.repositories

import com.example.myapplication.models.FilmResponse
import com.example.myapplication.models.api.FilmAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class FilmRepositoryImpl(private val retrofit: Retrofit) : FilmRepository {

    override fun getAllFilms(): Flow<FilmResponse> =
        flow {
            try {
                emit(retrofit.create(FilmAPI::class.java).getAllFilms())
            }
            catch (e: Exception){
                println("ERROR")
            }
        }

}