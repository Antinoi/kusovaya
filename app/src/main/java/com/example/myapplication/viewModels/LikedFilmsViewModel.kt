package com.example.myapplication.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.database.tables.LikedFilm

import java.util.concurrent.Executors

class LikedFilmsViewModel : ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()

    val search: MutableList<LikedFilm> = mutableListOf()


    var likes = MutableLiveData<MutableList<LikedFilm>>()
    init {
        likes.postValue(mutableListOf())
    }




    fun addData(context: Context, likedFilm: LikedFilm) {
        executorService.execute {
            val id = CinemaDB.getInstance(context)
                .likedFilmDao().add(likedFilm)
            likes.postValue(likes.value.apply {
                this?.add(LikedFilm(id, likedFilm.idFilm, likedFilm.idUser))
            })
            //неэффективно
            // getAll(context)
        }
    }

    fun getAll(context: Context) {
        executorService.execute {
            likes.postValue(
                CinemaDB.getInstance(context)
                    .likedFilmDao().select().toMutableList()
            )
        }
    }

    fun delete(context: Context, idFilm: Long, idUser: Long) {
        executorService.execute {

            CinemaDB.getInstance(context)
                .likedFilmDao().delete(idFilm, idUser)


            getAll(context)

        }
    }


    fun searchByUser(context: Context, idUser: Long){
        executorService.execute {


            val elemets = CinemaDB.getInstance(context)
                .likedFilmDao().getByUser(idUser)?.toMutableList()


            if(elemets != null){
                likes.postValue(
                    elemets.toMutableList()
                )
            }




        }
    }

    companion object {
        private lateinit var likedFilmsViewModel: LikedFilmsViewModel

        private val TAG = LikedFilmsViewModel::class.java.simpleName
    }

}