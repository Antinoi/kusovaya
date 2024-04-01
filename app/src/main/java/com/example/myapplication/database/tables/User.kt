package com.example.myapplication.database.tables

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity("users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId") val id: Long = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("password") val password: String,
    @ColumnInfo("email") val email: String
)


data class UserWithSeancesToGo(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "idUser"
    )
    val seancesToGo: List<SeanceToGo>
)

data class UserWithLikedFilms(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "idUser"
    )
    val likedFilms: List<LikedFilm>
)

