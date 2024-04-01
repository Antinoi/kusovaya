package com.example.myapplication.models.repositories


import android.net.ConnectivityManager
import android.util.Log
import com.example.myapplication.database.CinemaDatabase
import com.example.myapplication.database.tables.Film
import com.example.myapplication.database.tables.Seance
import com.example.myapplication.database.tables.Zal
import com.example.myapplication.models.api.FilmAPI
import com.example.myapplication.models.filmsForApp
import com.example.myapplication.models.toFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import kotlin.math.min

class FilmRepositoryImpl(
    private val retrofit: Retrofit,
    private val cinemaDatabase: CinemaDatabase,
    private val connectivityManager: ConnectivityManager
) : FilmRepository {

    override fun getAllFilms(userId: Long): Flow<List<filmsForApp>> =
        flow {
            try {
                // Проверяем наличие интернет-соединения

                val neededFilms = mutableListOf<filmsForApp>()
                var fimalFilms: List<filmsForApp>

                // Если есть интернет, загружаем данные с сервера


                Log.d("ERROR", "считает, что есть подключение")

                val filmResponse = retrofit.create(FilmAPI::class.java).getAllFilms()
                val films: List<Film> = filmResponse.toFilms()

                Log.d("ERROR", "ретрофит поймал")


                try {
                    Log.d("ERROR", "начало заполнение БД")

                    val zals = withContext(Dispatchers.IO) {
                        cinemaDatabase.zalDao().select()
                    }



                    if (zals.isNullOrEmpty()) {
                        withContext(Dispatchers.IO) {
                            cinemaDatabase.zalDao().add(Zal(1, "Большой зал"))
                            cinemaDatabase.zalDao().add(Zal(2, "Малый зал"))

                        }
                    }



                    Log.d("ERROR", "залы заполнены")


//                        withContext(Dispatchers.IO){
//                            cinemaDatabase.seanceDao().deleteAll()
//                        }


                    for (i in 0 until min(films.size, 5)) {

                        val existingFilm = withContext(Dispatchers.IO) {
                            cinemaDatabase.filmDao().getByAttr(films[i].title, films[i].poster)
                        }
                        if (existingFilm == null) {
                            withContext(Dispatchers.IO) {
                                cinemaDatabase.filmDao().add(films[i])
                            }
                        }

//                            withContext(Dispatchers.IO){
//                                cinemaDatabase.filmDao().delete(films[i].id)
//                            }


                        val currentFilm = withContext(Dispatchers.IO) {
                            cinemaDatabase.filmDao().getByAttr(films[i].title, films[i].poster)
                        }

                        if (currentFilm != null) {
                            val seances = withContext(Dispatchers.IO) {
                                cinemaDatabase.seanceDao().getByFilm(currentFilm.id)
                            }

                            if (seances.isNullOrEmpty()) {


                                withContext(Dispatchers.IO) {
                                    cinemaDatabase.seanceDao()
                                        .add(Seance(0, "31.02", "10:00", 1, currentFilm.id))

                                    cinemaDatabase.seanceDao()
                                        .add(Seance(0, "30.02", "21:00", 2, currentFilm.id))

                                }


                            }

                            val likes = withContext(Dispatchers.IO) {
                                cinemaDatabase.likedFilmDao().getByAttr(userId, currentFilm.id)
                            }

                            if(likes == null){
                                neededFilms.add(
                                    filmsForApp(
                                        currentFilm.id,
                                        currentFilm.title,
                                        currentFilm.year,
                                        currentFilm.ageRating,
                                        currentFilm.movieLength,
                                        currentFilm.description,
                                        currentFilm.genre,
                                        currentFilm.poster,
                                        false
                                    ))
                            }else{
                                neededFilms.add(
                                    filmsForApp(
                                        currentFilm.id,
                                        currentFilm.title,
                                        currentFilm.year,
                                        currentFilm.ageRating,
                                        currentFilm.movieLength,
                                        currentFilm.description,
                                        currentFilm.genre,
                                        currentFilm.poster,
                                        true
                                    ))
                            }

                        } else {
                            withContext(Dispatchers.IO) {
                                cinemaDatabase.filmDao().add(films[i])

                                val film = cinemaDatabase.filmDao()
                                    .getByAttr(films[i].title, films[i].poster)

                                cinemaDatabase.seanceDao()
                                    .add(Seance(0, "31.02", "12:00", 1, film!!.id))

                                cinemaDatabase.seanceDao()
                                    .add(Seance(0, "30.02", "18:30", 2, film!!.id))
                            }

                        }


                    }

//
//                        val filmsNow = withContext(Dispatchers.IO){
//                            cinemaDatabase.filmDao().select()
//                        }
//
//                        val seancesNow = withContext(Dispatchers.IO){
//                            cinemaDatabase.seanceDao().select()
//                        }
//
//                        Log.d("ERROR", "$filmsNow")
//                        Log.d("ERROR", "$seancesNow")



                    Log.d("ERROR", "БД ЗАПОЛНЕНА")
                    fimalFilms = neededFilms
                    Log.d("ERROR", " фильмы передаются $neededFilms")

                    emit(fimalFilms)

                } catch (e: Exception) {
                    Log.d("ERROR", "Что-то не так с подгрузкой в БД")
                }


            } catch (e: Exception) {
                Log.e("API_ERROR", "Ошибка при выполнении запроса: ${e.message}")

                Log.d("ERROR", "Что-то не так с подгрузкой в Retrofit")

                val neededFilms = mutableListOf<filmsForApp>()


                val likedfilms = withContext(Dispatchers.IO) {
                    cinemaDatabase.likedFilmDao().getByUser(userId)
                }


                Log.d("ERROR", "сеансы находятся")

                val realfilm = withContext(Dispatchers.IO) {
                    cinemaDatabase.filmDao().select()
                }

                Log.d("ERROR", " фильмы в бд $realfilm")


                Log.d("ERROR", " лайки в бд $likedfilms")


                if (likedfilms.isNotEmpty()) {
                    for (i in 0 until likedfilms.size) {
                        for (j in 0 until realfilm.size)

                            if (realfilm[j].id == likedfilms[i].idFilm) {
                                neededFilms.add(
                                    filmsForApp(
                                        realfilm[j].id,
                                        realfilm[j].title,
                                        realfilm[j].year,
                                        realfilm[j].ageRating,
                                        realfilm[j].movieLength,
                                        realfilm[j].description,
                                        realfilm[j].genre,
                                        realfilm[j].poster,
                                        true
                                    )
                                )
                            } else {
                                neededFilms.add(
                                    filmsForApp(
                                        realfilm[j].id,
                                        realfilm[j].title,
                                        realfilm[j].year,
                                        realfilm[j].ageRating,
                                        realfilm[j].movieLength,
                                        realfilm[j].description,
                                        realfilm[j].genre,
                                        realfilm[j].poster,
                                        false
                                    )
                                )
                            }

                    }

                } else {
                    for (j in 0 until realfilm.size) {
                        neededFilms.add(
                            filmsForApp(
                                realfilm[j].id,
                                realfilm[j].title,
                                realfilm[j].year,
                                realfilm[j].ageRating,
                                realfilm[j].movieLength,
                                realfilm[j].description,
                                realfilm[j].genre,
                                realfilm[j].poster,
                                false
                            )
                        )
                    }
                }


                Log.d("ERROR", " фильмы передаются $neededFilms")

                if (neededFilms.isNotEmpty()) {
                    // Отправляем данные из локальной БД
                    emit(neededFilms)


                } else {
                    Log.d("ERROR", "Пустая база данных, интернета тоже нет")
                }
            }


        }
    }