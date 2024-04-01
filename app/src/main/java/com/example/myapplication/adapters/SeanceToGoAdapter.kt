package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.database.views.SeanceCard
import com.example.myapplication.databinding.SeanceItemBinding
import com.example.myapplication.viewModels.SeanceViewModel

class SeanceToGoAdapter(private val onNotGoClicked: (idSeans: Long)->Unit):
    RecyclerView.Adapter<SeanceToGoAdapter.ViewHolder>() {


    private lateinit var seanceViewModel: SeanceViewModel









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
        return ViewHolder(
            SeanceItemBinding
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
            itemBinding.wantToGoButton.text = "не иду"

            itemBinding.wantToGoButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onNotGoClicked(seances[position].seanceId)

                }

            }


        }


    }
    companion object {
        val TAG = SeancesAdapter::class.java.simpleName



    }
}
