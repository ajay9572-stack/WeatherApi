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
import com.example.weatherapi.databinding.ActivitySignUpScreenBinding

class SignUpScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpScreenBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView( this,R.layout.activity_sign_up_screen)
            with (binding) {
                btnReg.setOnClickListener {
           intent = Intent(this@SignUpScreen, MainActivity2::class.java)
                    startActivity(intent)
            }
            }
    }
}