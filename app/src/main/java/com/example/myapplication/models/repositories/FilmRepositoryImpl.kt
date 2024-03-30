package com.example.myapplication.models.repositories


import android.net.ConnectivityManager
import android.util.Log
import com.example.myapplication.database.CinemaDatabase
import com.example.myapplication.database.tables.Film
import com.example.myapplication.database.tables.Seance
import com.example.myapplication.database.tables.Zal
import com.example.myapplication.models.api.FilmAPI
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

    override fun getAllFilms(): Flow<List<Film>> =
        flow {
            try {
                // Проверяем наличие интернет-соединения
                val isConnected = isConnectedToInternet()

                // Если есть интернет, загружаем данные с сервера
                if (isConnected) {

                    Log.d("ERROR", "считает, что есть подключение")

                    val filmResponse = retrofit.create(FilmAPI::class.java).getAllFilms()
                    val films: List<Film> = filmResponse.toFilms()


                    try {
                        Log.d("ERROR", "начало заполнение БД")

                        val zals = withContext(Dispatchers.IO) {
                            cinemaDatabase.zalDao().select()
                        }

                        if (zals.isNullOrEmpty()) {
                            withContext(Dispatchers.IO) {
                                cinemaDatabase.zalDao().add(Zal(1, "Большой зал"))
                                cinemaDatabase.zalDao().add(Zal(2, "Большой зал"))

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







                            val seances = withContext(Dispatchers.IO) {
                                cinemaDatabase.seanceDao().getByFilm(films[i].id)
                            }
                            Log.d("ERROR", "сеансы находятся")

                            val realfilm = withContext(Dispatchers.IO) {
                                cinemaDatabase.filmDao().select()
                            }

                            Log.d("ERROR", " фильмы в бд $realfilm")


                            val currentFilm =withContext(Dispatchers.IO) {
                                cinemaDatabase.filmDao().getByAttr(films[i].title,films[i].poster)
                            }

                            if (seances.isNullOrEmpty()) {

                                if(currentFilm != null){
                                    withContext(Dispatchers.IO) {
                                        cinemaDatabase.seanceDao().add(Seance(0,"31.02", "10:00",1, currentFilm.id))

                                        cinemaDatabase.seanceDao().add(Seance(0, "30.02", "10:00",2,  currentFilm.id))

                                    }
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

                    } catch (e: Exception) {
                        Log.e("ERROR", "база данных не заполнена", e)
                    }




                    emit(films)

                } else {

                    // Если интернета нет, загружаем данные из локальной базы данных

                    val localFilmList = cinemaDatabase.filmDao().select()

                    if (!localFilmList.isNullOrEmpty()) {
                        // Отправляем данные из локальной БД
                        emit(localFilmList)
                    } else {
                        Log.d("ERROR", "Пустая база данных, интернета тоже нет")
                    }
                }

            } catch (e: Exception) {
                println("ERROR: ${e.message}")
                Log.d("ERROR", "Что-то не так с подгрузкой в Retrofit")
            }
        }

    private fun isConnectedToInternet(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


}