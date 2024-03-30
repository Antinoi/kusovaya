package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.database.views.SeanceCard


@Dao
interface SeanceCardDao {


    @Query("SELECT * FROM seancecard")
    fun select():List<SeanceCard>


}