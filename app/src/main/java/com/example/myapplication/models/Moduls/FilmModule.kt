package com.example.myapplication.models.Moduls

import com.example.myapplication.models.Retrofit
import com.example.myapplication.models.repositories.FilmRepository
import com.example.myapplication.models.repositories.FilmRepositoryImpl
import com.example.myapplication.viewModels.FilmViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val filmModule = module {
    single<FilmRepository> {
        FilmRepositoryImpl(Retrofit.retrofit)
    }
    viewModel { FilmViewModel(get()) }

}