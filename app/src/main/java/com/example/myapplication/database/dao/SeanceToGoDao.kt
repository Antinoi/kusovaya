package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.database.tables.SeanceToGo


@Dao
interface SeanceToGoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(seanceToGo: SeanceToGo): Long
    @Query("DELETE FROM seancesToGo WHERE idUser = :idUser AND idSeance = :idSeance")
    fun delete(idUser: Long, idSeance: Long)

    @Query("SELECT * FROM seancesToGo WHERE idUser = :id")
    fun getByUser(id: Long): List<SeanceToGo>?

    @Query("SELECT * FROM seancesToGo")
    fun select():List<SeanceToGo>

}