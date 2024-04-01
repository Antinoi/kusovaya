package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myapplication.database.CinemaDatabase
import com.example.myapplication.database.tables.User
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewModels.UserViewModel


class MainActivity : AppCompatActivity() {
//values
    private lateinit var binding: ActivityMainBinding
    private lateinit var cinemaDatabase: CinemaDatabase
    private val itemModel: UserViewModel by viewModels()


//what's happenning
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)








        binding.loginButton.setOnClickListener{

            if(binding.loginTextView.text.isNullOrBlank()){
                binding.loginTextView.error = "Введите логин"

            } else if(binding.passwordTextEdit.text.isNullOrBlank()) {

                binding.passwordTextEdit.error = "Введите пароль"
            }
                else{
                    binding.loginTextView.error = ""
                    binding.passwordTextEdit.error = ""
                    binding.emailTextEdit.error = ""



                    itemModel.getByNameANDPassword(this, binding.loginTextView.text.toString(), binding.passwordTextEdit.text.toString()).observeOnce(this) { user ->
                        user?.let {



                            val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                                putExtra("login",user.name)
                                putExtra("password", user.password)
                                putExtra("id", user.id)}
                            startActivity(intent)




                        } ?: run {
                            binding.loginTextView.error = "Пройти регистрацию"
                            binding.passwordTextEdit.error = "Пройти регистрацию"
                            binding.emailTextEdit.error = "Пройти регистрацию"

                        }



                    }


                }

        }

        binding.registrationButton.setOnClickListener {

            if(binding.loginTextView.text.isNullOrBlank() ){
                binding.loginTextView.error = "Введите логин"

            } else if(binding.passwordTextEdit.text.isNullOrBlank()) {
                binding.passwordTextEdit.error = "Введите пароль"
            }
            else if(binding.emailTextEdit.text.isNullOrBlank()){
                binding.emailTextEdit.error = "Введите email"

            }else{
                binding.loginTextView.error = ""
                binding.passwordTextEdit.error = ""
                binding.emailTextEdit.error = ""



                itemModel.getByNameANDPassword(this, binding.loginTextView.text.toString(), binding.passwordTextEdit.text.toString()).observeOnce(this) { user ->
                    user?.let {


                            binding.loginTextView.error = "Пользователь уже существует"
                            binding.passwordTextEdit.error = "Пользователь уже существует"
                            binding.emailTextEdit.error = "Пользователь уже существует"



                    } ?: run {

                            itemModel.addData(this, User(0,binding.loginTextView.text.toString(), binding.passwordTextEdit.text.toString(), binding.emailTextEdit.text.toString()))
                            runOnUiThread {
                                val toast = Toast.makeText(applicationContext, "Вы зарегистрировались", Toast.LENGTH_SHORT)
                                toast.show()
                            }



                    }



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