package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.FilmDao
import com.example.myapplication.database.dao.LikedFilmDao
import com.example.myapplication.database.dao.SeanceDao
import com.example.myapplication.database.dao.SeanceToGoDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.database.dao.ZalDao
import com.example.myapplication.database.tables.Film
import com.example.myapplication.database.tables.LikedFilm
import com.example.myapplication.database.tables.Seance
import com.example.myapplication.database.tables.SeanceToGo
import com.example.myapplication.database.tables.User
import com.example.myapplication.database.tables.Zal

@Database(entities =
[User::class,
    Seance::class,
    Film::class,
    Zal::class,
    SeanceToGo::class,
    LikedFilm::class ], version = 6)

abstract class CinemaDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun seanceDao(): SeanceDao
    abstract fun filmDao(): FilmDao
    abstract fun zalDao(): ZalDao
    abstract fun seanceToGoDao(): SeanceToGoDao
    abstract fun likedFilmDao(): LikedFilmDao
}


object CinemaDB{
    private lateinit var instanse: CinemaDatabase
    fun getInstance(context: Context): CinemaDatabase{
        if(!::instanse.isInitialized){
            instanse = Room.databaseBuilder(context,
                CinemaDatabase::class.java,
                "cinema-db").build()
        }
        return instanse;
    }
}