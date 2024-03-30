package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.database.dao.FilmDao
import com.example.myapplication.database.dao.LikedFilmDao
import com.example.myapplication.database.dao.SeanceCardDao
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
import com.example.myapplication.database.views.SeanceCard
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Database(entities =
[User::class,
    Seance::class,
    Film::class,
    Zal::class,
    SeanceToGo::class,
    LikedFilm::class ], views = [SeanceCard::class], version = 1)

abstract class CinemaDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun seanceDao(): SeanceDao
    abstract fun filmDao(): FilmDao
    abstract fun zalDao(): ZalDao
    abstract fun seanceToGoDao(): SeanceToGoDao
    abstract fun likedFilmDao(): LikedFilmDao

    abstract fun SeanceCardDao(): SeanceCardDao


}




object CinemaDB{
    private lateinit var instanse: CinemaDatabase

    val migration6to7 = object : Migration(7, 6) {

            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE seances\n" +
                            "DROP COLUMN zal,\n" +
                            "DROP COLUMN film,\n" +
                            "DROP COLUMN poster;"
                )
            }

                // Удаляем данные из таблицы seances
//                database.execSQL("DELETE FROM seances")
//
//
//                database.execSQL("CREATE TABLE IF NOT EXISTS `seances` " +
//                        "(`idFilm` INTEGER NOT NULL, `seanceId` INTEGER NOT NULL, " +
//                        "`time` TEXT NOT NULL, `data` TEXT NOT NULL, `idZal` INTEGER NOT NULL, " +
//                        "PRIMARY KEY(`seanceId`), " +
//                        "FOREIGN KEY(`idFilm`) REFERENCES `films`(`filmId`) ON UPDATE CASCADE ON DELETE CASCADE, " +
//                        "FOREIGN KEY(`idZal`) REFERENCES `zals`(`zalId`) ON UPDATE CASCADE ON DELETE CASCADE)")
//
//            }
        }

//    val migration6to7 = object : Migration(6, 7) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//
//        }
//    }

    fun getInstance(context: Context): CinemaDatabase{
        if(!::instanse.isInitialized){
            instanse = Room.databaseBuilder(context,
                CinemaDatabase::class.java,
                "cinema-db").build()
        }
        return instanse;
    }
}

val databaseModule = module {
    single {
        CinemaDB.getInstance(androidContext())
    }
}