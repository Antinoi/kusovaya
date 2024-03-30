package com.example.myapplication.database.tables

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("zals")
data class Zal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "zalId") val id: Long = 0,
    @ColumnInfo("name") val name: String

)

data class ZalWithSeances(
    @Embedded val zal: Zal,
    @Relation(
        parentColumn = "zalId",
        entityColumn = "idZal"
    )
    val seances: List<Seance>
)
