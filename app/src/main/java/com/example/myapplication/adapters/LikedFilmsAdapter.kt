package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.database.tables.LikedFilm

import com.example.myapplication.database.views.UsersLikedFilms
import com.example.myapplication.databinding.LikedfilmItemBinding



class LikedFilmsAdapter:
    RecyclerView.Adapter<LikedFilmsAdapter.ViewHolder>() {


    private lateinit var likedFilmsAdapter: LikedFilmsAdapter

   private lateinit var likedFilmListener: OnLikedFilmsListener


    interface OnLikedFilmsListener {

        fun onGoLikedFilms(likedFilm: LikedFilm)
    }


    private lateinit var filmListener: OnLikedFilmsListener
    fun setLikedFilmsListener(listener: OnLikedFilmsListener) {
        this.filmListener = listener
    }


    /**
     * данные
     * */

    private var userLikedFilms: MutableList<UsersLikedFilms> = mutableListOf()
    fun getUsersLikedFilms() = userLikedFilms
    fun setUsersLikedFilms(value: List<UsersLikedFilms>){
        userLikedFilms = value.toMutableList()
    }

    /**
     * что делать при создании "обслуживателя" ячейки
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LikedfilmItemBinding
            .inflate(
                LayoutInflater
                    .from(parent.context)))
    }
    /**
     * количество данных
     * */
    override fun getItemCount(): Int = userLikedFilms.size
    /**
     * что делать в случае привязки "обслуживателя" ячейки
     * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "REDRAW WITH ${userLikedFilms[position]}")



        holder.itemBinding.likedFilmNameTextView.text = userLikedFilms[position].title
        holder.itemBinding.likedYearTextView.text = userLikedFilms[position].year
        holder.itemBinding.likedGenreTextView.text = userLikedFilms[position].genre
        holder.itemBinding.likedDescriptionTextView.text = userLikedFilms[position].description

        val imagecontext = holder.itemBinding.likedPosterImageView.context
        Glide.with(imagecontext)
            .load(userLikedFilms[position].poster)
            .into(holder.itemBinding.likedPosterImageView)




    }
    /**
     * "обслуживатель"
     * */


    /**кнопка идти на сеанс в холдере* */
    inner class ViewHolder(val itemBinding: LikedfilmItemBinding):
        RecyclerView.ViewHolder(itemBinding.root){

        init{
            itemBinding.unlikeFilmButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    filmListener.onGoLikedFilms(
                        LikedFilm(userLikedFilms[position].likedFilmId,
                            userLikedFilms[position].userId,
                            userLikedFilms[position].filmId


                        )
                    )
                }

            }


        }


    }
    companion object {
        val TAG = LikedFilmsAdapter::class.java.simpleName



    }
}