package com.bintangfajarianto.submission2.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import com.bintangfajarianto.submission2.databinding.ActivitySplashBinding
import com.bintangfajarianto.submission2.di.ViewModelFactory
import com.bintangfajarianto.submission2.ui.authentication.AuthActivity
import com.bintangfajarianto.submission2.ui.home.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels {
        ViewModelFactory(this)
    }

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel.getToken().observe(this) { token ->
            this.token = token
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (token.isNullOrEmpty()) {
                Log.i(TAG, "Authentication")

                val intentAuth = Intent(this@SplashActivity, AuthActivity::class.java)
                startActivity(intentAuth)
                finish()
            } else {
                Log.i(TAG, "Home")

                val intentMain = Intent(this@SplashActivity, HomeActivity::class.java)
                intentMain.putExtra(HomeActivity.EXTRA_TOKEN, token)
                startActivity(intentMain)
                finish()
            }
        }, SPLASH_DELAY)
    }

    companion object {
        private const val TAG = "SPLASH DECISION"
        private const val SPLASH_DELAY: Long = 3000
    }
}
