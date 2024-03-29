package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.database.tables.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(user: User): Long
    @Query("DELETE FROM users WHERE userId = :id")
    fun delete(id: Long)
    @Query("SELECT * FROM users WHERE name = :name AND password = :password")
    fun getByNameANDPassword(name: String, password: String): User?
    @Query("SELECT * FROM users WHERE userId = :id")
    fun getById(id: Long): User?
    @Query("UPDATE users SET name = :name, password = :password, email = :email WHERE userId = :id")
    fun update(name: String, password: String, email: String, id: Long)
    @Query("SELECT * FROM users")
    fun select():List<User>?

}