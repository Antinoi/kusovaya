package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapters.PosterAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.viewModels.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.min



// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), PosterAdapter.OnPosterClickListener {


    private lateinit var binding: FragmentHomeBinding


    lateinit var viewPager2: ViewPager2
    lateinit var handler: Handler
    lateinit var adapter: PosterAdapter


    lateinit var listofposters : MutableList<String>
    lateinit var filmTitles: MutableList<String>
    lateinit var filmYears: MutableList<String>
    lateinit var filmRatings: MutableList<String>
    lateinit var filmLengths: MutableList<String>
    lateinit var filmGenres: MutableList<String>
    lateinit var filmDescriptions: MutableList<String>

    private val filmViewModel: FilmViewModel by viewModel()





    private var param1: String? = null
    private var param2: String? = null

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         filmTitles = mutableListOf()
         listofposters = mutableListOf()
         filmYears = mutableListOf()
         filmRatings = mutableListOf()
         filmLengths = mutableListOf()
         filmGenres = mutableListOf()
         filmDescriptions = mutableListOf()


         //создание карусели с наполнением
         viewPager2 = binding.viewPager2

         adapter = PosterAdapter(
             listofposters,
             filmTitles,
                     filmYears,
                     filmRatings,
                     filmLengths,
                     filmGenres,
                     filmDescriptions,
             viewPager2,
             this@HomeFragment)

         with(viewPager2) {
             // Задаём отступы, чтобы по бокам были видны части соседних карточек

             setPadding(250, 0, 250, 0)
         }

         handler = Handler(Looper.myLooper()!!)

         viewPager2.adapter = adapter




         //изменение характеристик постера
         val transformer = CompositePageTransformer()
         transformer.addTransformer(MarginPageTransformer(60))
         transformer.addTransformer { page, position ->
             val r = 1 - abs(position)
             page.scaleY = 0.85f + r * 0.14f
             page.alpha = 0.5f + r

         }

         viewPager2.setPageTransformer(transformer)


         viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
             override fun onPageSelected(position: Int) {
                 super.onPageSelected(position)

             }

         })



         //Log.d("ERROR", "ОНО дебил")


         viewLifecycleOwner.lifecycleScope.launchWhenResumed {
             filmViewModel.getAllFilms().collect { films ->

                 // Так как collect выполняется в Main потоке, мы можем обновлять UI напрямую


                 //Log.d("ERROR", "ОНО ЗАШЛО В КОЛЛЕКТ")



                 for (i in 0 until min(films.size, 10)){
                     try {
                         // Ваш код

                         listofposters.add(films[i].poster)
                         filmTitles.add(films[i].title)
                         filmYears.add(films[i].year.toString())
                         filmRatings.add(films[i].ageRating.toString())
                         filmLengths.add(films[i].movieLength.toString())
                         filmGenres.add(films[i].genre)
                         filmDescriptions.add(films[i].description)




//                         Log.d("ERROR", "$i")
                     } catch (e: Exception) {
//                         Log.e("ERROR", "Exception on iteration $i: ${e.message}")
//                         Log.d("ERROR", "${films}")
                         break // Остановите цикл после возникновения исключения, если это уместно
                     }



                 }


//                 Log.d("ERROR", "Вошёл в обновление")
//                 Log.d("ERROR", "$listofposters")
//                 Log.d("ERROR", "$filmTitles")


                 adapter.updateData(
                     listofposters,
                     filmTitles,
                     filmYears,
                     filmRatings,
                     filmLengths,
                     filmGenres,
                     filmDescriptions )

             }






         }



         //changing the view of pager
         viewPager2.offscreenPageLimit = 3
         viewPager2.clipToPadding = false
         viewPager2.clipChildren = false
         viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
         (viewPager2.parent as ViewGroup).clipToPadding = false
         (viewPager2.parent as ViewGroup).clipChildren = false


        //что-то с аргументами
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }



//just binding settings
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }




    //when you choose poster
    override fun onPosterClick(
        filmTitle: String,
        filmYear: String,
        filmRating: String,
        filmLength: String,
        filmGenre: String,
        filmDescription: String,
        ) {
        binding.filmNametextView.text = filmTitle
        binding.yearTextView.text = filmYear
        binding.reitTextView.text = "$filmRating+"
        binding.longTextView.text = "$filmLength м"
        binding.genretextView.text = filmGenre
        binding.descriptionTextView.text = filmDescription
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}