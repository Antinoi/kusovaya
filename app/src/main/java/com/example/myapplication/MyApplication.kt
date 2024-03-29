package com.example.myapplication

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.myapplication.database.databaseModule
import com.example.myapplication.models.Moduls.filmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    //  lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        //  container = AppDataContainer(this)

        startKoin() {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModules)
        }


    }
}

val connectivityModule = module {
    single {
        val context: Context = get() // Получение контекста приложения из Koin
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}

val appModules = listOf(filmModule, databaseModule, connectivityModule)