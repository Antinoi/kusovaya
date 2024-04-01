package com.example.myapplication.viewModels

import androidx.lifecycle.ViewModel
import com.example.myapplication.models.repositories.FilmRepository

class FilmViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getAllFilms(userId: Long) = filmRepository.getAllFilms(userId)
}