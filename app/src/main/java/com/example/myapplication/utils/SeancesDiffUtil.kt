package com.example.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.database.views.SeanceCard

class SeancesDiffUtil(private val oldList: List<SeanceCard>,
                      private val newList: List<SeanceCard>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].seanceId == newList[newItemPosition].seanceId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}