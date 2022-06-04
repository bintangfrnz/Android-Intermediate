package com.bintangfajarianto.submission1.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.core.text.bold
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.data.remote.response.ListStoryItem
import com.bintangfajarianto.submission1.databinding.ActivityDetailBinding
import com.bintangfajarianto.submission1.utils.TimeFormatter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()

        val story = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY)
        if (story != null) {
            binding.apply {
                toolbarTitle.text = resources.getString(R.string.detail_title, story.name)
                detailUsername.text = story.name
                detailDesc.text = SpannableStringBuilder()
                    .bold { append(story.name) }
                    .append(": ")
                    .append(story.description)
                detailDate.text = TimeFormatter.getTimeAgo(story.createdAt)

                likeButton.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        Toast.makeText(
                            this@DetailActivity,
                            "${applicationContext.getString(R.string.hit_like)} (T)",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DetailActivity,
                            "${applicationContext.getString(R.string.hit_like)} (F)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                commentButton.setOnClickListener {
                    Toast.makeText(
                        this@DetailActivity,
                        R.string.hit_like,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                shareButton.setOnClickListener {
                    Toast.makeText(
                        this@DetailActivity,
                        R.string.hit_like,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Glide.with(applicationContext)
                    .load(R.drawable.img_default_profile)
                    .apply(RequestOptions().override(100,100))
                    .into(detailAvatar)

                Glide.with(applicationContext)
                    .load(story.photoUrl)
                    .into(detailImage)
            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}