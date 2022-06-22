package com.bintangfajarianto.submission2.adapter

import android.app.Activity
import android.content.Intent
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.text.bold
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.data.local.entity.Story
import com.bintangfajarianto.submission2.databinding.StoryCardItemBinding
import com.bintangfajarianto.submission2.ui.detail.DetailActivity
import com.bintangfajarianto.submission2.utils.TimeFormatter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class StoryAdapter : PagingDataAdapter<Story, StoryAdapter.ListViewHolder>(DiffCallback) {

    class ListViewHolder(private val binding: StoryCardItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {
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
            StoryCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with (holder) {
            val story = getItem(position)
            if (story != null) {
                bind(story)
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}