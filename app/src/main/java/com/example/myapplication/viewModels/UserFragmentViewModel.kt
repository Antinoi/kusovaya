package com.example.myapplication.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.views.UsersLikedFilms
import java.util.concurrent.Executors

class UserFragmentViewModel: ViewModel() {
    var films = MutableLiveData<MutableList<UsersLikedFilms>>()

    private val executorService = Executors.newSingleThreadExecutor()

    val searchFilm = MutableLiveData<UsersLikedFilms?>()


    //надо создать новый view для сеансов, на которые пойду и связывающие сеансы, на которые пойду


}