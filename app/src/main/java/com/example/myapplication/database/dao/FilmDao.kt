package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.database.tables.Film
import com.example.myapplication.database.tables.FilmWithSeances


@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(film: Film): Long
    @Query("DELETE FROM films WHERE filmId = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM films WHERE title = :title AND poster = :poster")
    fun getByAttr(title: String, poster: String):Film?

    @Query("SELECT * FROM films WHERE filmId = :id ")
    fun getById(id: Long):Film?
    @Query("SELECT * FROM films")
    fun select():List<Film>



    @Transaction
    @Query("SELECT * FROM films")
    fun getAllFilmsWithSeances(): List<FilmWithSeances>

    @Transaction
    @Query("SELECT * FROM films WHERE filmId =:id ")
    fun getFilmByIdWithSeances(id: Long): FilmWithSeances?

}