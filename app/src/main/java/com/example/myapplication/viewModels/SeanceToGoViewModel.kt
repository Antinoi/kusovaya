package com.example.myapplication.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.database.tables.SeanceToGo

import java.util.concurrent.Executors

class SeanceToGoViewModel : ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()

    val search: MutableList<SeanceToGo> = mutableListOf()


    var goes = MutableLiveData<MutableList<SeanceToGo>>()
    init {
        goes.postValue(mutableListOf())
    }




    fun addData(context: Context, seanceToGo: SeanceToGo) {
        executorService.execute {
            val id = CinemaDB.getInstance(context)
                .seanceToGoDao().add(seanceToGo)
            goes.postValue(goes.value.apply {
                this?.add(SeanceToGo(id, seanceToGo.idSeance, seanceToGo.idUser))
            })
            //неэффективно
            // getAll(context)
        }
    }

    fun getAll(context: Context) {
        executorService.execute {
            goes.postValue(
                CinemaDB.getInstance(context)
                    .seanceToGoDao().select().toMutableList()
            )
        }
    }

    fun delete(context: Context, idSeance: Long, idUser: Long) {
        executorService.execute {

            CinemaDB.getInstance(context)
                .seanceToGoDao().delete(idSeance, idUser)


            getAll(context)

        }
    }


    fun searchByUser(context: Context, idUser: Long){
        executorService.execute {


            val elemets = CinemaDB.getInstance(context)
                .seanceToGoDao().getByUser(idUser)?.toMutableList()


            if(elemets != null){
                goes.postValue(
                    elemets.toMutableList()
                )
            }




        }
    }

    companion object {
        private lateinit var seanceToGoViewModel: SeanceToGoViewModel

        private val TAG = SeanceToGoViewModel::class.java.simpleName
    }

}