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
import com.example.myapplication.adapters.LikedFilmsAdapter
import com.example.myapplication.adapters.SeanceToGoAdapter
import com.example.myapplication.database.CinemaDatabase
import com.example.myapplication.databinding.FragmentUserBinding
import com.example.myapplication.utils.FilmsDiffUtil
import com.example.myapplication.utils.SeancesDiffUtil
import com.example.myapplication.viewModels.LikedFilmsViewModel
import com.example.myapplication.viewModels.SeanceViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var database: CinemaDatabase
    private lateinit var filmadapter: LikedFilmsAdapter

    private lateinit var seanceadapter: SeanceToGoAdapter


    private val filmModel: LikedFilmsViewModel by viewModels()

    private val seanceModel: SeanceViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getLong("userId")
        val login = arguments?.getString("login")
        val password = arguments?.getString("password")


        filmadapter = LikedFilmsAdapter(){
                idFilm ->
            filmModel.delete(requireContext(), userId!!, idFilm)

        }

        seanceadapter = SeanceToGoAdapter(){
            idSeans ->
            seanceModel.delete(requireContext(), userId!!, idSeans)
        }




        binding.likeAndGoRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.likeAndGoRecycleView.adapter = filmadapter
        binding.likeAndGoRecycleView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        binding.userNameTextView.text = login

        val email = seanceModel.getEmail(requireContext(), userId!!).toString()
        binding.emailTextView.text = email

        filmModel.likes.observe(viewLifecycleOwner){
            val productDiffUtilCallback =
                FilmsDiffUtil(filmadapter.getUsersLikedFilms(), it)
            val productDiffResult =
                DiffUtil.calculateDiff(productDiffUtilCallback)
            filmadapter.setUsersLikedFilms(it)
            productDiffResult.dispatchUpdatesTo(filmadapter)
        }

        filmModel.getAll(requireContext(), userId)


        seanceModel.foundLikedSeances.observe(viewLifecycleOwner){
            val productDiffUtilCallback =
                SeancesDiffUtil(seanceadapter.getSeances(), it)
            val productDiffResult =
                DiffUtil.calculateDiff(productDiffUtilCallback)
            seanceadapter.setSeances(it)
            productDiffResult.dispatchUpdatesTo(seanceadapter)
        }

        seanceModel.getAllSeancesToGo(requireContext(), userId)

        binding.showLikedButton.setOnClickListener {
            binding.likeAndGoRecycleView.adapter = filmadapter
        }

        binding.showGoingButton.setOnClickListener {
            binding.likeAndGoRecycleView.adapter = seanceadapter
        }







    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }



    companion object {



    }


}