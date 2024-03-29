package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.SeancesAdapter
import com.example.myapplication.database.CinemaDatabase
import com.example.myapplication.database.tables.Seance
import com.example.myapplication.databinding.FragmentSeanceBinding
import com.example.myapplication.utils.SeancesDiffUtil
import com.example.myapplication.viewModels.SeanceViewModel


class SeanceFragment : Fragment(), SeancesAdapter.OnSeanceListener {

    private lateinit var binding: FragmentSeanceBinding
    private lateinit var database: CinemaDatabase
    private lateinit var adapter: SeancesAdapter
    private val itemModel: SeanceViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SeancesAdapter()
        adapter.setSeanceListener(this)

        binding.seanceRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.seanceRecycleView.adapter = adapter
        binding.seanceRecycleView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))




        itemModel.seances.observe(viewLifecycleOwner){
            val productDiffUtilCallback =
                SeancesDiffUtil(adapter.getSeances(), it)
            val productDiffResult =
                DiffUtil.calculateDiff(productDiffUtilCallback)
            adapter.setSeances(it)
            productDiffResult.dispatchUpdatesTo(adapter)
        }
        itemModel.getAll(requireContext())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {



    }

    override fun onGoSeance(seance: Seance) {
        TODO("Not yet implemented")
    }
}