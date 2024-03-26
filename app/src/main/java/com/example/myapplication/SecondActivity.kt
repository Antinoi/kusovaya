package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivitySecondBinding
import com.example.myapplication.fragments.HomeFragment
import com.example.myapplication.fragments.SeanceFragment
import com.example.myapplication.fragments.UserFragment


class SecondActivity : AppCompatActivity() {
    //values
    private lateinit var binding: ActivitySecondBinding


    //what's happenning
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

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