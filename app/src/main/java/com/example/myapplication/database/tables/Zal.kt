package com.example.myapplication.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("zals")
data class Zal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "zalId") val id: Long = 0,
    @ColumnInfo("name") val name: String

)
