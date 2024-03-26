package com.example.myapplication

import android.app.Application
import com.example.myapplication.models.Moduls.filmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    //  lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        //  container = AppDataContainer(this)

        startKoin() {
            androidLogger()
            androidContext(this@MyApplication)
            modules(filmModule)
        }


    }
}