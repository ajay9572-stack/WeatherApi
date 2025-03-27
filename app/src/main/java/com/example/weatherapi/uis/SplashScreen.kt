package com.example.weatherapi.uis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.weatherapi.R
import com.example.weatherapi.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)
             with(binding) {
                 btnLogin.setOnClickListener {
                     intent = Intent(this@SplashScreen, LogInScreen::class.java)
                     startActivity(intent)
                 }
                 btnSignup.setOnClickListener {
                     intent= Intent(this@SplashScreen,SignUpScreen::class.java)
                     startActivity(intent)
                 }
             }
    }
}