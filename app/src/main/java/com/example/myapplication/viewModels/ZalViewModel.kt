package com.example.myapplication.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.database.tables.Zal

import java.util.concurrent.Executors

class ZalViewModel : ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()

    val search: MutableList<Zal> = mutableListOf()


    var zals = MutableLiveData<MutableList<Zal>>()
    init {
        zals.postValue(mutableListOf())
    }




    fun addData(context: Context, zal: Zal) {
        executorService.execute {
            val id = CinemaDB.getInstance(context)
                .zalDao().add(zal)
            zals.postValue(zals.value.apply {
                this?.add(Zal(id, zal.name))
            })
            //неэффективно
            // getAll(context)
        }
    }

    fun getAll(context: Context) {
        executorService.execute {
            zals.postValue(
                CinemaDB.getInstance(context)
                    .zalDao().select()?.toMutableList()
            )
        }
    }

     fun delete(context: Context, id: Long) {
        executorService.execute {

            CinemaDB.getInstance(context)
                .zalDao().delete(id)


            getAll(context)

        }
    }


    fun searchByName(context: Context, name: String){
        executorService.execute {


            val element = CinemaDB.getInstance(context)
                .zalDao().getByName(name)

            if(element != null){
                search.add(element)
                zals.postValue(search)
            }



        }
    }

    companion object {
        private lateinit var zalViewModel: ZalViewModel

        private val TAG = ZalViewModel::class.java.simpleName
    }

}