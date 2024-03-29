package com.example.myapplication.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB

import com.example.myapplication.database.tables.Seance
import java.util.concurrent.Executors

class SeanceViewModel : ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()

    val search: MutableList<Seance> = mutableListOf()


    var seances = MutableLiveData<MutableList<Seance>>()
    init {
        seances.postValue(mutableListOf())
    }




    fun addData(context: Context, seance: Seance) {
        executorService.execute {
            val id = CinemaDB.getInstance(context)
                .seanceDao().add(seance)
            seances.postValue(seances.value.apply {
                this?.add(Seance(id, seance.data, seance.time, seance.idZal, seance.idFilm))
            })
            //неэффективно
            // getAll(context)
        }
    }

    fun getAll(context: Context) {
        executorService.execute {
            seances.postValue(
                CinemaDB.getInstance(context)
                    .seanceDao().select().toMutableList()
            )
        }
    }

    fun delete(context: Context, id: Long) {
        executorService.execute {

            CinemaDB.getInstance(context)
                .seanceDao().delete(id)


            getAll(context)

        }
    }


    fun searchByAttr(context: Context, idFilm: Long, data: String, time: String, idZal: Long){
        executorService.execute {


            val element = CinemaDB.getInstance(context)
                .seanceDao().getByAttr(idFilm, data, time, idZal)

            if(element != null){
                search.add(element)
                seances.postValue(search)
            }



        }
    }

    companion object {
        private lateinit var seanceViewModel: SeanceViewModel

        private val TAG = SeanceViewModel::class.java.simpleName
    }

}