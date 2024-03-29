package com.example.myapplication.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId") val id: Long = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("password") val password: String,
    @ColumnInfo("email") val email: String
)
