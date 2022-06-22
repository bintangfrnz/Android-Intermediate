package com.bintangfajarianto.submission2.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.adapter.LoadingStateAdapter
import com.bintangfajarianto.submission2.adapter.StoryAdapter
import com.bintangfajarianto.submission2.data.local.entity.Story
import com.bintangfajarianto.submission2.databinding.ActivityHomeBinding
import com.bintangfajarianto.submission2.di.ViewModelFactory
import com.bintangfajarianto.submission2.ui.authentication.AuthActivity
import com.bintangfajarianto.submission2.ui.publish.PublishActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvStories: RecyclerView
    private lateinit var storyAdapter: StoryAdapter

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(this)
    }

    private var token: String = BLANK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Get and Use Token
        token = intent.getStringExtra(EXTRA_TOKEN).toString()

        showRecyclerView()

//        homeViewModel.responseStories.observe(this) {
//            setDisplayNoData(it.listStory.isEmpty())
//
//            val listStories = ArrayList<ListStoryItem>()
//
//            for (user in it.listStory) {
//                listStories.add(user)
//            }
//
//            showRecyclerView(listStories)
//
//            // Fill HomeWidget
//            lifecycleScope.launchWhenResumed {
//                val listRemoteItem = ArrayList<RemoteItem>()
//
//                Log.i("HomeWidget", "Start Fetching")
//
//                for (user in it.listStory) {
//                    listRemoteItem.add(
//                        RemoteItem(
//                            user.name,
//                            ImageHandler.urlToBitmap(user.photoUrl, applicationContext)
//                        )
//                    )
//                }
//                HomeWidgetItems.remoteItems = listRemoteItem
//
//                Log.i("HomeWidget", "Done")
//
//                // TODO: Notify onDataSetChange to RemoteAdapter
//            }
//        }

        setUpAction()
    }

    override fun onResume() {
        super.onResume()
        getAllStories()
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

    private fun showRecyclerView() {
        rvStories = binding.rvStories
        storyAdapter = StoryAdapter()

        storyAdapter.addLoadStateListener {
            if (it.source.refresh is LoadState.Error ||
                (it.source.refresh is LoadState.NotLoading
                        && storyAdapter.itemCount < 1
                        && it.append.endOfPaginationReached)
            ) {
                setDisplayNoData(true)
            } else {
                setDisplayNoData(false)
            }

            binding.swipeRefresh.isRefreshing = it.source.refresh is LoadState.Loading
        }

        try {
            rvStories.layoutManager = LinearLayoutManager(this)
            rvStories.adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { storyAdapter.retry() }
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun getAllStories() {
        homeViewModel.getAllStories(resources.getString(R.string.token, token)).observe(this) {
            updateRecyclerViewData(it)
        }
    }

    private fun setUpAction() {
        binding.swipeRefresh.setOnRefreshListener {
            getAllStories()
        }
    }

    private fun setDisplayNoData(isNoData: Boolean) {
        binding.imgNoData.visibility = if (isNoData) View.VISIBLE else View.GONE
        binding.textNoData.visibility = if (isNoData) View.VISIBLE else View.GONE
    }

    private fun updateRecyclerViewData(stories: PagingData<Story>) {
        val recyclerViewState = rvStories.layoutManager?.onSaveInstanceState()

        storyAdapter.submitData(lifecycle, stories)
        rvStories.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    companion object {
        private const val BLANK = ""
        const val EXTRA_TOKEN = "extra_token"
    }
}