package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.database.views.UsersLikedFilms

@Dao

interface UsersLikedFilmsDao {

    @Query("SELECT * FROM userslikedfilms")
    fun select():List<UsersLikedFilms>

    @Query("SELECT * FROM userslikedfilms WHERE userId = :userId")
    fun getByUserId(userId: Long):List<UsersLikedFilms>

    @Query("SELECT * FROM userslikedfilms WHERE userId = :userId AND poster =:poster AND title = :title")
    fun getByAttr(userId: Long, poster: String, title: String): UsersLikedFilms?

}