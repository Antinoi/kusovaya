package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.database.tables.LikedFilm


@Dao
interface LikedFilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(likedFilm: LikedFilm): Long
    @Query("DELETE FROM likedFilms WHERE idUser = :idUser AND idFilm = :idFilm")
    fun delete(idUser: Long, idFilm: Long)

    @Query("SELECT * FROM likedFilms WHERE idUser = :id")
    fun getByUser(id: Long): List<LikedFilm>?

    @Query("SELECT * FROM likedFilms")
    fun select():List<LikedFilm>?

}