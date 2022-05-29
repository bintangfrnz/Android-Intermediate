package com.bintangfajarianto.submission1.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bintangfajarianto.submission1.databinding.ActivityAuthenticationBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}