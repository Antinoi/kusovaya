package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.database.tables.Seance

@Dao
interface SeanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(seance: Seance): Long
    @Query("DELETE FROM seances WHERE seanceId = :id")
    fun delete(id: Long)
    @Query("SELECT * FROM seances")
    fun select():List<Seance>
    @Query("SELECT * FROM seances WHERE idFilm = :idFilm AND data = :data AND time = :time AND idZal = :idZal")
    fun getByAttr(idFilm: Long, data: String, time: String, idZal: Long):Seance?

}