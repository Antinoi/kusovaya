package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
//values
    private lateinit var binding: ActivityMainBinding


//what's happenning
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    binding.loginButton.setOnClickListener{
        var login = binding.loginTextView.text.toString()
        val intent = Intent(this@MainActivity, SecondActivity::class.java).apply { this.putExtra("login",login) }
        startActivity(intent)


    }

    }
}