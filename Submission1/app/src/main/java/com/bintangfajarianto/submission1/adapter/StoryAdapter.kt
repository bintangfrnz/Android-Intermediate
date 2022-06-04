package com.bintangfajarianto.submission1.adapter

import android.app.Activity
import android.content.Intent
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.text.bold
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.data.remote.response.ListStoryItem
import com.bintangfajarianto.submission1.databinding.CardStoryBinding
import com.bintangfajarianto.submission1.ui.detail.DetailActivity
import com.bintangfajarianto.submission1.utils.TimeFormatter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class StoryAdapter(private val listStories: ArrayList<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: CardStoryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(story: ListStoryItem) {
            val context = itemView.context

            binding.apply {
                storyUsername.text = story.name
                storyDesc.text = SpannableStringBuilder()
                    .bold { append(story.name) }
                    .append(": ")
                    .append(story.description)
                storyDate.text = TimeFormatter.getTimeAgo(story.createdAt)

                likeButton.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        Toast.makeText(
                            context,
                            "${context.getString(R.string.hit_like)} (T)",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "${context.getString(R.string.hit_like)} (F)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                commentButton.setOnClickListener {
                    Toast.makeText(context, R.string.hit_like, Toast.LENGTH_SHORT).show()
                }
                shareButton.setOnClickListener {
                    Toast.makeText(context, R.string.hit_like, Toast.LENGTH_SHORT).show()
                }

                Glide.with(context)
                    .load(R.drawable.img_default_profile)
                    .apply(RequestOptions().override(100,100))
                    .into(storyAvatar)

                Glide.with(context)
                    .load(story.photoUrl)
                    .into(storyImage)
            }

            itemView.setOnClickListener {
                val intentDetail = Intent(context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_STORY, story)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair(binding.storyAvatar, "avatar"),
                        Pair(binding.storyUsername, "username"),
                        Pair(binding.storyImage, "image"),
                        Pair(binding.storyDesc, "desc"),
                        Pair(binding.storyDate, "date"),
                    )
                context.startActivity(intentDetail, optionsCompat.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            CardStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with (holder) {
            val story = listStories[position]
            bind(story)
        }
    }

    override fun getItemCount(): Int = listStories.size
}