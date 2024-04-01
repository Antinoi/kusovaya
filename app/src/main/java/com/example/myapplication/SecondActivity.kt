package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        val login = this.intent.extras?.getString("login")
        val password = this.intent.extras?.getString("password")
        val userId = this.intent.extras?.getLong("id")


        val bundle = Bundle().apply {
            putLong("userId", userId!!)

        }

        val homeFragment = HomeFragment().apply {
            arguments = bundle
        }
        val seanceFragment = SeanceFragment().apply {
            arguments = bundle
        }
        val userFragment = UserFragment().apply {
            arguments = bundle
        }


        replaceFragment(homeFragment)





        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.homeNav -> replaceFragment(homeFragment)
                R.id.seanceNav -> replaceFragment(seanceFragment)
                R.id.profileNav -> replaceFragment(userFragment)
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