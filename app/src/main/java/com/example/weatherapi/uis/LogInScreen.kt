package com.example.weatherapi.uis

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.weatherapi.R
import com.example.weatherapi.databinding.ActivityLogInScreenBinding

class LogInScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLogInScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_log_in_screen)
       with(binding) {
           btnLogintwo.setOnClickListener {
               intent= Intent(this@LogInScreen,MainActivity2::class.java)
               startActivity(intent)
           }
           }
       }
    }
