package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.database.views.SeanceCard


@Dao
interface SeanceCardDao {



    @Transaction
    @Query("SELECT * FROM seancecard")
    fun select():List<SeanceCard>

    @Transaction
    @Query("SELECT * FROM seancecard WHERE seanceId = :seanceId")
    fun getBySeance(seanceId: Long):SeanceCard


}