package com.example.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.database.views.UsersLikedFilms


class FilmsDiffUtil (private val oldList: List<UsersLikedFilms>,
                     private val newList: List<UsersLikedFilms>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].likedFilmId == newList[newItemPosition].likedFilmId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}