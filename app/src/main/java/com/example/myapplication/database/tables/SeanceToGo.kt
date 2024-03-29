package com.example.myapplication.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("seancesToGo",
    foreignKeys = arrayOf(
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("idUser"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Seance::class,
            parentColumns = arrayOf("seanceId"),
            childColumns = arrayOf("idSeance"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)

    )
)
data class SeanceToGo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seanceToGoId") val id: Long = 0,

    @ColumnInfo("idSeance") val idSeance: Long = 0,
    @ColumnInfo("idUser") val idUser: Long = 0
)
