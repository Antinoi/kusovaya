package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplication.R

class PosterAdapter(
    private val posterList: MutableList<String>,

    private val filmTitles: MutableList<String>,
    private val filmYears: MutableList<String>,
    private val filmRatings : MutableList<String>,
    private val filmLengths: MutableList<String>,
    private val filmGenres: MutableList<String>,
    private var filmDescriptions: MutableList<String>,
    private var isCheckedList: MutableList<Boolean>,


    private val viewPager2: ViewPager2,
    private val listener: OnPosterClickListener,
    private val likeListener: OnCheckClickListener):
    RecyclerView.Adapter<PosterAdapter.PosterViewHolder>()

{


    interface OnCheckClickListener {
        fun like(
            isChecked: Boolean,
            isCheckedBD:Boolean,
            poster: String,
            title: String

        )


    }

    interface OnPosterClickListener {
        fun onPosterClick(
            filmTitle: String,
            filmYear: String,
            filmRating: String,
            filmLength: String,
            filmGenre: String,
            filmDescription: String
        )



        


    }




    init {
        // Устанавливаем начальную позицию ViewPager2 для бесконечного скроллинга
        val initialPosition = Integer.MAX_VALUE / 2
        if (posterList.isNotEmpty()) {
            val startPosition = initialPosition - initialPosition % posterList.size
            viewPager2.setCurrentItem(startPosition, false)
        }
    }

    class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.posterView)
        val checkbox: CheckBox = itemView.findViewById((R.id.checkBox))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contaner_poster, parent, false)
        return PosterViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Возвращаем очень большое значение для создания иллюзии бесконечного скролла
        return Integer.MAX_VALUE
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {

        if (posterList.isNotEmpty()){

            // Вычисляем "реальную" позицию элемента в списке, используя остаток от деления
            val realPosition = position % posterList.size
            //private val posterList: MutableList<Int> в параметры класса
            //holder.posterImageView.setImageResource(posterList[realPosition])


            val context = holder.posterImageView.context
            Glide.with(context)
                .load(posterList[realPosition])
                .into(holder.posterImageView)

            if(isCheckedList.isNotEmpty()){
                holder.checkbox.isChecked = isCheckedList[realPosition]
            }
            else{
                holder.checkbox.isChecked = false
            }


            holder.checkbox.setOnClickListener {
                likeListener.like(holder.checkbox.isChecked, isCheckedList[realPosition], posterList[realPosition], filmTitles[realPosition] )
            }

            holder.itemView.setOnClickListener {

                listener.onPosterClick(
                    filmTitles[realPosition],
                filmYears[realPosition],
                filmRatings[realPosition],
                filmLengths[realPosition],
                filmGenres[realPosition],
                filmDescriptions[realPosition]
                )



            }

        }




    }


    suspend fun updateData(
        listofposters: MutableList<String>,
        newFilmTitles: MutableList<String>,
        newfilmYears: MutableList<String>,
        newfilmRatings: MutableList<String>,
        newfilmLengths: MutableList<String>,
        newfilmGenres: MutableList<String>,
        newfilmDescriptions: MutableList<String>,
        newisCheckedList: MutableList<Boolean>
    ) {

//        Log.d("ERROR", "В ОБНОВЛЕНИИ!!!!")
//
//        Log.d("ERROR", "Начало updateData")
//        Log.d("ERROR", "До очистки: listofposters=${listofposters.size},$listofposters , newFilmTitles=${newFilmTitles.size}, $newFilmTitles")



            posterList.addAll(listofposters)
            filmTitles.addAll(newFilmTitles)
            filmYears.addAll(newfilmYears)
            filmRatings.addAll(newfilmRatings)
            filmLengths.addAll(newfilmLengths)
            filmGenres.addAll(newfilmGenres)
            filmDescriptions.addAll(newfilmDescriptions)
            isCheckedList.addAll(newisCheckedList)





        val initialPosition = Integer.MAX_VALUE / 2
        if (posterList.isNotEmpty()) {
            val startPosition = initialPosition - initialPosition % posterList.size
            viewPager2.setCurrentItem(startPosition, false)
        }



        notifyDataSetChanged()
    }


}