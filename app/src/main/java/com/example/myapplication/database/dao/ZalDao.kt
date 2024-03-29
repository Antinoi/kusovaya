package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.myapplication.database.tables.Zal

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

}