package com.example.myapplication.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.database.tables.SeanceToGo
import com.example.myapplication.database.views.SeanceCard
import java.util.concurrent.Executors

class SeanceViewModel : ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()

    var search: MutableList<SeanceCard> = mutableListOf()

    var email = MutableLiveData<String>()

    var seanceToGo = MutableLiveData<SeanceToGo>()



    val foundLikedSeances = MutableLiveData<MutableList<SeanceCard>>()

    var seances = MutableLiveData<MutableList<SeanceCard>>()
    init {
        seances.postValue(mutableListOf())
        email.postValue("")
        foundLikedSeances.postValue(mutableListOf())
        seanceToGo.postValue(SeanceToGo(0, 0, 0))

    }



    fun go (context: Context, userId: Long, seanceId: Long){
        executorService.execute{
            CinemaDB.getInstance(context).seanceToGoDao().add(SeanceToGo(0, seanceId, userId))

            getAllSeancesToGo(context, userId)
        }
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


    fun getAllSeancesToGo(context: Context, idUser: Long) {
        executorService.execute {
            search = mutableListOf()

            val goes = CinemaDB.getInstance(context).seanceToGoDao().getByUser(idUser)

            for (i in 0 until goes.size) {
                search.add(
                    CinemaDB.getInstance(context).SeanceCardDao().getBySeance(goes[i].idSeance)
                )
            }

            foundLikedSeances.postValue(search)
        }
    }

    fun findSeanceToGo(context: Context, idUser: Long, idSeance: Long){
        executorService.execute{
            seanceToGo.postValue(CinemaDB.getInstance(context).seanceToGoDao().getByAttr(idUser, idSeance))
        }
    }


    fun delete(context: Context, idUser: Long, isSeance: Long) {
        executorService.execute {

            CinemaDB.getInstance(context)
                .seanceToGoDao().delete(idUser, isSeance)


            getAllSeancesToGo(context, idUser)

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