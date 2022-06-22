package com.bintangfajarianto.submission1.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.adapter.StoryAdapter
import com.bintangfajarianto.submission1.data.model.HomeWidgetItems
import com.bintangfajarianto.submission1.data.model.RemoteItem
import com.bintangfajarianto.submission1.data.remote.response.ListStoryItem
import com.bintangfajarianto.submission1.databinding.ActivityHomeBinding
import com.bintangfajarianto.submission1.di.ViewModelFactory
import com.bintangfajarianto.submission1.ui.authentication.AuthActivity
import com.bintangfajarianto.submission1.ui.publish.PublishActivity
import com.bintangfajarianto.submission1.utils.ImageHandler

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private var token: String = BLANK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        // Show Loading & Refresh
        homeViewModel.isLoading.observe(this) {
            showLoading(it)
            showRefresh(it)
        }

        // Get and Use Token
        token = intent.getStringExtra(EXTRA_TOKEN).toString()

        homeViewModel.responseStories.observe(this) {
            setDisplayNoData(it.listStory.isEmpty())

            val listStories = ArrayList<ListStoryItem>()

            for (user in it.listStory) {
                listStories.add(user)
            }

            showRecyclerView(listStories)

            // Fill HomeWidget
            lifecycleScope.launchWhenResumed {
                val listRemoteItem = ArrayList<RemoteItem>()

                Log.i("HomeWidget", "Start Fetching")

                for (user in it.listStory) {
                    listRemoteItem.add(
                        RemoteItem(
                            user.name,
                            ImageHandler.urlToBitmap(user.photoUrl, applicationContext)
                        )
                    )
                }
                HomeWidgetItems.remoteItems = listRemoteItem

                Log.i("HomeWidget", "Done")

                // TODO: Notify onDataSetChange to RemoteAdapter
            }
        }

        setUpAction()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getAllStoriesByRefresh(resources.getString(R.string.token, token))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_menu -> {
                val intentAdd = Intent(this@HomeActivity, PublishActivity::class.java)
                startActivity(intentAdd)
            }
            R.id.logout_menu -> {
                homeViewModel.saveToken(BLANK)
                Toast.makeText(this, R.string.logout_valid, Toast.LENGTH_SHORT).show()

                val intentAuth = Intent(this@HomeActivity, AuthActivity::class.java)
                startActivity(intentAuth)
                finish()
            }
            R.id.language_menu -> {
                val intentLanguage = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intentLanguage)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerView(listStoryItem: ArrayList<ListStoryItem>) {
        val rvStories = binding.rvStories

        rvStories.layoutManager = LinearLayoutManager(this)
        rvStories.adapter = StoryAdapter(listStoryItem)
    }

    private fun setUpAction() {
        binding.swipeRefresh.setOnRefreshListener {
            homeViewModel.getAllStoriesByRefresh(resources.getString(R.string.token, token))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRefresh(isRefreshing: Boolean) {
        binding.swipeRefresh.isRefreshing = isRefreshing
    }

    private fun setDisplayNoData(isNoData: Boolean) {
        binding.imgNoData.visibility = if (isNoData) View.VISIBLE else View.GONE
        binding.textNoData.visibility = if (isNoData) View.VISIBLE else View.GONE
    }

    companion object {
        private const val BLANK = ""
        const val EXTRA_TOKEN = "extra_token"
    }
}