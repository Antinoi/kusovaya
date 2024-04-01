package com.example.myapplication.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.database.tables.Film
import java.util.concurrent.Executors

class FilmSavedViewModel : ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()

    val search: MutableList<Film> = mutableListOf()


    val searchData = MutableLiveData<Film?>()


    var films = MutableLiveData<MutableList<Film>>()


    var savedFilm: Film = Film (0, "",0,0, 0, "","", "")
    init {
        films.postValue(mutableListOf())
    }




    fun addData(context: Context, film: Film) {
        executorService.execute {
            val id = CinemaDB.getInstance(context)
                .filmDao().add(film)
            films.postValue(films.value.apply {
                this?.add(Film(id, film.title, film.year, film.ageRating, film.movieLength, film.genre, film.description, film.poster))
            })
            //неэффективно
            // getAll(context)
        }
    }

    fun getAll(context: Context) {
        executorService.execute {
            films.postValue(
                CinemaDB.getInstance(context)
                    .filmDao().select().toMutableList()
            )
        }
    }

    fun delete(context: Context, id: Long) {
        executorService.execute {

            CinemaDB.getInstance(context)
                .filmDao().delete(id)


            getAll(context)

        }
    }


    fun searchByAttr(context: Context, title: String, poster: String): LiveData<Film?> {

        executorService.execute {
            val filmFound = CinemaDB.getInstance(context)
                .filmDao().getByAttr(title, poster)

            Log.d(TAG, "найденный фильм при попытке лайка: $filmFound")
            searchData.postValue(filmFound)
        }

        return searchData
    }

    fun searchById(context: Context, id: Long) {
        executorService.execute {


            val element = CinemaDB.getInstance(context)
                .filmDao().getById(id)

            if(element != null){
                search.add(element)
                films.postValue(search)
            }



        }
    }




    companion object {
        private lateinit var filmSavedViewModel: FilmSavedViewModel

        private val TAG = FilmSavedViewModel::class.java.simpleName
    }

}