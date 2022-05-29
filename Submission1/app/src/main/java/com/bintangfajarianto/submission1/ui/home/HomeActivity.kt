package com.bintangfajarianto.submission1.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.databinding.ActivityHomeBinding
import com.bintangfajarianto.submission1.di.ViewModelFactory
import com.bintangfajarianto.submission1.ui.authentication.AuthActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
    private val homeViewModel: HomeViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_menu -> {
                homeViewModel.saveToken(BLANK)

                val intentAuth = Intent(this@HomeActivity, AuthActivity::class.java)
                startActivity(intentAuth)
                finish()
            }
            R.id.language_menu -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_TOKEN = "extra_token"
        const val BLANK = ""
    }
}