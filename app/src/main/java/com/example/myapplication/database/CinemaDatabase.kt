package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Database(entities =
[User::class,
    Seance::class,
    Film::class,
    Zal::class,
    SeanceToGo::class,
    LikedFilm::class ], version = 7)

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

    val migration6to7 = object : Migration(6, 7) {

            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("CREATE TABLE IF NOT EXISTS seances (" +
                        "seanceId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "data TEXT NOT NULL," +
                        "time TEXT NOT NULL," +
                        "idZal INTEGER NOT NULL," +
                        "idFilm INTEGER NOT NULL," +
                        "zal TEXT NOT NULL," +
                        "film TEXT NOT NULL," +
                        "poster TEXT NOT NULL," +
                        "FOREIGN KEY(idZal) REFERENCES zals(zalId) ON UPDATE CASCADE ON DELETE CASCADE," +
                        "FOREIGN KEY(idFilm) REFERENCES films(filmId) ON UPDATE CASCADE ON DELETE CASCADE)")
            }
        }

    fun getInstance(context: Context): CinemaDatabase{
        if(!::instanse.isInitialized){
            instanse = Room.databaseBuilder(context,
                CinemaDatabase::class.java,
                "cinema-db").addMigrations(migration6to7).build()
        }
        return instanse;
    }
}

val databaseModule = module {
    single {
        CinemaDB.getInstance(androidContext())
    }
}