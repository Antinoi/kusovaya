package com.example.myapplication.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.database.tables.Seance
import com.example.myapplication.database.views.SeanceCard
import java.util.concurrent.Executors

class SeanceViewModel : ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()

    val search: MutableList<Seance> = mutableListOf()

    var email = MutableLiveData<String>()


    var seances = MutableLiveData<MutableList<SeanceCard>>()
    init {
        seances.postValue(mutableListOf())
        email.postValue("")
    }



    fun getEmail(context: Context, userId: Long){
        executorService.execute{
            email.postValue(CinemaDB.getInstance(context).userDao().getById(userId)!!.email)
        }

    }


    fun getAll(context: Context) {
        executorService.execute {
            seances.postValue(
                CinemaDB.getInstance(context).SeanceCardDao().select().toMutableList()
            )
        }
    }

//    suspend fun getAllSUS(context: Context): MutableList<Seance> {
//        executorService.execute {
//            seances.postValue(
//                CinemaDB.getInstance(context)
//                    .seanceDao().select().toMutableList()
//            )
//        }
//        return withContext(Dispatchers.IO) {
//            CinemaDB.getInstance(context).seanceDao().select().toMutableList()// Пример вызова метода getAll() вашей DAO
//        }
//
//
//    }


    fun delete(context: Context, id: Long) {
        executorService.execute {

            CinemaDB.getInstance(context)
                .seanceDao().delete(id)


            getAll(context)

        }
    }


//    fun searchByAttr(context: Context, idFilm: Long, data: String, time: String, idZal: Long){
//        executorService.execute {
//
//
//            val element = CinemaDB.getInstance(context)
//                .seanceDao().getByAttr(idFilm, data, time, idZal)
//
//            if(element != null){
//                search.add(element)
//                seances.postValue(search)
//            }
//
//
//
//        }
//    }

    companion object {
        private lateinit var seanceViewModel: SeanceViewModel

        private val TAG = SeanceViewModel::class.java.simpleName
    }

}