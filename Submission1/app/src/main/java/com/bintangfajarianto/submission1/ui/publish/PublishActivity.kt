package com.bintangfajarianto.submission1.ui.publish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bintangfajarianto.submission1.databinding.ActivityPublishBinding

class PublishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublishBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}