package com.example.myapplication.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.database.tables.LikedFilm
import com.example.myapplication.database.views.UsersLikedFilms
import java.util.concurrent.Executors

class LikedFilmsViewModel : ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()

    val search: MutableList<UsersLikedFilms> = mutableListOf()

    val addElement: LikedFilm = LikedFilm(0, 0, 0)

    var filmTitleNow: String = ""

    var state = MutableLiveData<Boolean>()

    var likes = MutableLiveData<MutableList<UsersLikedFilms>>()

    var liked: UsersLikedFilms = UsersLikedFilms(0, 0,0,"", "", "","", "")
    init {
        likes.postValue(mutableListOf())
        state.postValue(false)

    }



    fun add(context: Context, idFilm: Long, idUser: Long){

        executorService.execute{
            if(CinemaDB.getInstance(context).likedFilmDao().getByAttr(idUser, idFilm) == null){
                CinemaDB.getInstance(context).likedFilmDao().add(LikedFilm(0, idUser, idFilm))
                Log.d("LIKE", "произошло добавление")
                Log.d("LIKE", "$idFilm")
            }

            val film = CinemaDB.getInstance(context).filmDao().getById(idFilm)


            filmTitleNow = film!!.title

            Log.d(TAG, "Все лайкнутые фильмы: ${CinemaDB.getInstance(context).likedFilmDao().select().toMutableList()}")
            state.postValue(true)

            getAll(context)

        }




    }






    fun getAll(context: Context) {
        executorService.execute {
            likes.postValue(
                CinemaDB.getInstance(context)
                    .UsersLikedFilmsDao().select().toMutableList()
            )


        }
    }


    fun getByAttr(context: Context, userId: Long, poster: String, title: String): UsersLikedFilms {

        executorService.execute {

            val likedFound = CinemaDB.getInstance(context).UsersLikedFilmsDao().getByAttr(userId, poster, title)
            if(likedFound != null){
                liked = likedFound
            } else{
                liked = UsersLikedFilms(0, 0,0,"", "", "","", "")
            }
        }
        return liked
    }

    fun delete(context: Context, idUser: Long, idFilm: Long) {
        executorService.execute {

            CinemaDB.getInstance(context).likedFilmDao().delete(idUser, idFilm)

            Log.d("LIKE", "произошло удаление")
            Log.d("LIKE", "$idFilm")

            Log.d(TAG, "Все лайкнутые фильмы: ${CinemaDB.getInstance(context).likedFilmDao().select().toMutableList()}")

            val film = CinemaDB.getInstance(context).filmDao().getById(idFilm)


            filmTitleNow = film!!.title

            state.postValue(false)

            getAll(context)


        }
    }



    companion object {
        private lateinit var likedFilmsViewModel: LikedFilmsViewModel

        private val TAG = LikedFilmsViewModel::class.java.simpleName
    }

}