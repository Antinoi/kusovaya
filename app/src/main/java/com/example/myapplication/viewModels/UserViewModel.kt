package com.example.myapplication.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.database.tables.User
import java.util.concurrent.Executors

class UserViewModel: ViewModel() {
    private val executorService = Executors.newSingleThreadExecutor()


    var search: MutableList<User> = mutableListOf()

  

    val searchData = MutableLiveData<User?>()


    var users = MutableLiveData<MutableList<User>>()
    init {
        users.postValue(mutableListOf())
    }


    fun getByNameANDPassword(context: Context, name: String, password: String): LiveData<User?> {

        executorService.execute {
            val userFound = CinemaDB.getInstance(context)
                .userDao().getByNameANDPassword(name, password)


            searchData.postValue(userFound)
        }

        return searchData
    }
    fun addData(context: Context, user: User) {
        executorService.execute {
             CinemaDB.getInstance(context)
                .userDao().add(user)

        }
    }

    fun getAll(context: Context): List<User> {
        executorService.execute {
            search =
                CinemaDB.getInstance(context)
                    .userDao().select().toMutableList()

        }
        return search
    }

    fun delete(context: Context, id: Long) {
        executorService.execute {

            CinemaDB.getInstance(context)
                .userDao().delete(id)

        }
    }


    fun update(context: Context, name: String, password: String, email: String, id: Long){
        executorService.execute {

            CinemaDB.getInstance(context)
                .userDao().update(name, password, email, id)

        }
    }

    companion object {
        private lateinit var userViewModel: UserViewModel

        private val TAG = UserViewModel::class.java.simpleName
    }

}