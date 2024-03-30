package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.database.CinemaDB
import com.example.myapplication.databinding.ActivitySecondBinding
import com.example.myapplication.fragments.HomeFragment
import com.example.myapplication.fragments.SeanceFragment
import com.example.myapplication.fragments.UserFragment
import java.util.concurrent.Executors


class SecondActivity : AppCompatActivity() {
    //values
    private lateinit var binding: ActivitySecondBinding


    //what's happenning
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        val executorService = Executors.newSingleThreadExecutor()

        executorService.execute{
            Log.d("DATABASE", "onCreate: ${CinemaDB.getInstance(this).SeanceCardDao().select()}")
        }

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.homeNav -> replaceFragment(HomeFragment())
                R.id.seanceNav -> replaceFragment(SeanceFragment())
                R.id.profileNav -> replaceFragment(UserFragment())
            }

            true
        }

    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }


}