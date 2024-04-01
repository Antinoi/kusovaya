package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapters.PosterAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.viewModels.FilmSavedViewModel
import com.example.myapplication.viewModels.FilmViewModel
import com.example.myapplication.viewModels.LikedFilmsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.min





/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), PosterAdapter.OnPosterClickListener, PosterAdapter.OnCheckClickListener{


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
    lateinit var isCheckedList: MutableList<Boolean>

    private val filmViewModel: FilmViewModel by viewModel()



    private val likedFilmViewModel: LikedFilmsViewModel by viewModels()
    private val filmSavedViewModel: FilmSavedViewModel by viewModels()








     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         var wasHandled = false
         filmTitles = mutableListOf()
         listofposters = mutableListOf()
         filmYears = mutableListOf()
         filmRatings = mutableListOf()
         filmLengths = mutableListOf()
         filmGenres = mutableListOf()
         filmDescriptions = mutableListOf()
         isCheckedList = mutableListOf()

         val userId = arguments?.getLong("userId")

         Log.d("USER ID", "onViewCreated  userId: $userId")


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
             isCheckedList,
             viewPager2,
             this@HomeFragment,
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


         lifecycleScope.launch {

             likedFilmViewModel.likes.observe(viewLifecycleOwner) { likes ->

                 likes?.let {
                     if(isCheckedList.isNotEmpty()){

                         var position: MutableList<Int> = mutableListOf()

                         for(i in 0 until filmTitles.size){
                             if (filmTitles[i]  == likedFilmViewModel.filmTitleNow){
                                 position.add(i)  //% filmTitles.size
                             }
                         }

                         Log.d("POSITION", "onViewCreated: $position ")

                         for(j in 0 until position.size){

                             isCheckedList[position[j]] = likedFilmViewModel.state.value!!
                             Log.d("POSITION", "onViewCreated: ${isCheckedList[position[j]]} ")

                         }
                         Log.d("POSITION", "количество элементов: ${isCheckedList} ")


                     }

                 }








             }



             adapter.updateData(
                 listofposters,
                 filmTitles,
                 filmYears,
                 filmRatings,
                 filmLengths,
                 filmGenres,
                 filmDescriptions,
                 isCheckedList
             )
         }






         viewLifecycleOwner.lifecycleScope.launchWhenResumed {
             filmViewModel.getAllFilms(userId!!).collect { films ->



                 for (i in 0 until min(films.size, 5)){
                     try {
                         // Ваш код

                         listofposters.add(films[i].poster)
                         filmTitles.add(films[i].title)
                         filmYears.add(films[i].year.toString())
                         filmRatings.add(films[i].ageRating.toString())
                         filmLengths.add(films[i].movieLength.toString())
                         filmGenres.add(films[i].genre)
                         filmDescriptions.add(films[i].description)

                         Log.d("ПОСТЕРЫ", "onViewCreated: $i добавляется информация о фильмах ")

                         isCheckedList.add(films[i].liked)


                         Log.d("ПОСТЕРЫ", "список булеанов на момент $i: $isCheckedList  ")






//
                     } catch (e: Exception) {
//
                         break // Остановите цикл после возникновения исключения, если это уместно
                     }





                 }

                 Log.d("ПОСТЕРЫ", "Обновление данных адаптера")
                 adapter.updateData(
                     listofposters,
                     filmTitles,
                     filmYears,
                     filmRatings,
                     filmLengths,
                     filmGenres,
                     filmDescriptions,
                     isCheckedList)

             }











         }



         //changing the view of pager
         viewPager2.offscreenPageLimit = 3
         viewPager2.clipToPadding = false
         viewPager2.clipChildren = false
         viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
         (viewPager2.parent as ViewGroup).clipToPadding = false
         (viewPager2.parent as ViewGroup).clipChildren = false





    }



//just binding settings
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val userId = arguments?.getLong("userId")
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

    override fun like(isChecked: Boolean, isCheckedBD: Boolean, poster: String, title: String) {
        val userId = arguments?.getLong("userId")
        if(userId != null) {

            Log.d("LIKE SCLIKED", "like FILMID: $title ")

            var wasHandled = false

            filmSavedViewModel.searchByAttr(requireContext(), title, poster).observe(viewLifecycleOwner) { film ->
                film?.let {

                    if (!wasHandled) {
                        Log.d("LIKE SCLIKED", "like FILMID: $film ")

                        wasHandled = true

                        if(isChecked && !isCheckedBD){

                            val idFilm = film.id

                            Log.d("LIKE SCLIKED", "like FILMID: $idFilm ")

                            likedFilmViewModel.add(requireContext(), idFilm, userId)
                            wasHandled = false



                        }else if(!isChecked && isCheckedBD){
                            val idFilm = film.id
                            Log.d("LIKE SCLIKED", "like FILMID: $idFilm ")

                            likedFilmViewModel.delete(requireContext(),userId, idFilm)

                            Log.d("LIKE SCLIKED", "like all: ${likedFilmViewModel.getAll(requireContext())} ")
                            wasHandled = false

                        }else{
                            Log.d("LIKE ERROR", "состояние лайка фильма не изменилось")
                        }

                    }




                } ?: run {
                    Log.d("LIKE ERROR", "лайк не прошёл, в списке нет такого фильма")
                    wasHandled = false

                }
            }


        }

    }



    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)

            }
        })
    }


}