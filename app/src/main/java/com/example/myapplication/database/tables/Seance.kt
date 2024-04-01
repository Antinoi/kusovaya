package com.example.myapplication.database.tables

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("seances",
    foreignKeys = arrayOf(
        ForeignKey(entity = Zal::class,
            parentColumns = arrayOf("zalId"),
            childColumns = arrayOf("idZal"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Film::class,
            parentColumns = arrayOf("filmId"),
            childColumns = arrayOf("idFilm"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)

    )
)


data class Seance(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seanceId") val id: Long = 0,
    @ColumnInfo("data") val data: String,
    @ColumnInfo("time") val time: String,


    @ColumnInfo("idZal") val idZal: Long,
    @ColumnInfo("idFilm") val idFilm: Long,

)

data class SeancesWithSeancesToGo(
    @Embedded val seance: Seance,
    @Relation(
        parentColumn = "seanceId",
        entityColumn = "idSeance"
    )
    val seancesToGo: List<SeanceToGo>
)



