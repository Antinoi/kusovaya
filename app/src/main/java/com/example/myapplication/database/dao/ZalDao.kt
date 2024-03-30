package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.database.tables.Zal
import com.example.myapplication.database.tables.ZalWithSeances

@Dao
interface ZalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(zal: Zal): Long
    @Query("DELETE FROM zals WHERE zalId = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM zals WHERE name = :name")
    fun getByName(name: String): Zal?

    @Query("SELECT * FROM zals WHERE zalId = :id")
    fun getById(id: Long): Zal?
    @Query("SELECT * FROM zals")
    fun select():List<Zal>?




    @Transaction
    @Query("SELECT * FROM zals")
    fun getAllZalsWithSeances(): List<ZalWithSeances>

    @Transaction
    @Query("SELECT * FROM zals WHERE zalId =:id ")
    fun getZalByIdWithSeances(id: Long): ZalWithSeances?

}