package com.bintangfajarianto.submission2.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bintangfajarianto.submission2.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}