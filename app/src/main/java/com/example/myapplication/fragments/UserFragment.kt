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
import com.example.myapplication.database.CinemaDatabase
import com.example.myapplication.database.tables.LikedFilm
import com.example.myapplication.databinding.FragmentUserBinding
import com.example.myapplication.utils.FilmsDiffUtil
import com.example.myapplication.viewModels.LikedFilmsViewModel




/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment(), LikedFilmsAdapter.OnLikedFilmsListener {
    private lateinit var binding: FragmentUserBinding
    private lateinit var database: CinemaDatabase
    private lateinit var adapter: LikedFilmsAdapter
    private val itemModel: LikedFilmsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getLong("userId")
        val login = arguments?.getString("login")
        val password = arguments?.getString("password")


        adapter = LikedFilmsAdapter()
        adapter.setLikedFilmsListener(this)

        binding.likeAndGoRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.likeAndGoRecycleView.adapter = adapter
        binding.likeAndGoRecycleView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        binding.userNameTextView.text = login


        itemModel.likes.observe(viewLifecycleOwner){
            val productDiffUtilCallback =
                FilmsDiffUtil(adapter.getUsersLikedFilms(), it)
            val productDiffResult =
                DiffUtil.calculateDiff(productDiffUtilCallback)
            adapter.setUsersLikedFilms(it)
            productDiffResult.dispatchUpdatesTo(adapter)
        }
        itemModel.getAll(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onGoLikedFilms(likedFilm: LikedFilm) {
        TODO("Not yet implemented")
    }

    companion object {



    }
}