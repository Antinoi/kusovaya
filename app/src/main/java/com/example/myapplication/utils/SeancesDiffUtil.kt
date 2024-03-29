package com.example.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.database.tables.Seance

class SeancesDiffUtil(private val oldList: List<Seance>,
                      private val newList: List<Seance>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}