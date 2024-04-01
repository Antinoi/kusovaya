package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.database.tables.Seance
import com.example.myapplication.database.views.SeanceCard
import com.example.myapplication.databinding.SeanceItemBinding
import com.example.myapplication.viewModels.SeanceViewModel

/**
 * Адаптер для RecyclerView.
 * Связывает данные (массив) и UI (ячейки списка)
 * */
class SeancesAdapter():
    RecyclerView.Adapter<SeancesAdapter.ViewHolder>() {


    private lateinit var seanceViewModel: SeanceViewModel

    private lateinit var onSeanceListener: OnSeanceListener


    interface OnSeanceListener {

        fun onGoSeance(seance: Seance)
    }

    private lateinit var seanceListener: OnSeanceListener

    fun setSeanceListener(listener: OnSeanceListener) {
        this.seanceListener = listener
    }


    /**
     * данные
     * */

    private var seances: MutableList<SeanceCard> = mutableListOf()
    fun getSeances() = seances
    fun setSeances(value: List<SeanceCard>){
        seances = value.toMutableList()
    }

    /**
     * что делать при создании "обслуживателя" ячейки
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SeanceItemBinding
            .inflate(
                LayoutInflater
                .from(parent.context)))
    }
    /**
     * количество данных
     * */
    override fun getItemCount(): Int = seances.size
    /**
     * что делать в случае привязки "обслуживателя" ячейки
     * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "REDRAW WITH ${seances[position]}")



//        val context = holder.itemBinding.posterSeanceImageView2.context



//        val film = CinemaDB.getInstance(context)
//            .filmDao().getById(seances[position].idFilm)
//        val zal = CinemaDB.getInstance(context).zalDao().getById(seances[position].idZal)


            holder.itemBinding.filmTitleSTextView.text = seances[position].title
            holder.itemBinding.zalSeanceTextView2.text = seances[position].zalName
            holder.itemBinding.dataSeanceTextView.text = seances[position].data
            holder.itemBinding.timeSeanceTextView4.text = seances[position].time

            val imagecontext = holder.itemBinding.posterSeanceImageView2.context
            Glide.with(imagecontext)
                .load(seances[position].poster)
                .into(holder.itemBinding.posterSeanceImageView2)




    }
    /**
     * "обслуживатель"
     * */


    /**кнопка идти на сеанс в холдере* */
    inner class ViewHolder(val itemBinding: SeanceItemBinding):
        RecyclerView.ViewHolder(itemBinding.root){

        init{
            itemBinding.wantToGoButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    seanceListener.onGoSeance(
                        Seance(seances[position].seanceId,
                        seances[position].data,
                        seances[position].time,
                        seances[position].zalId,
                        seances[position].filmId,
                           )
                    )
                }

            }


        }


    }
    companion object {
        val TAG = SeancesAdapter::class.java.simpleName



    }
}
