package com.bintangfajarianto.submission1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.data.remote.response.ListStoryItem
import com.bintangfajarianto.submission1.databinding.CardStoryBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class StoryAdapter(private val listStories: ArrayList<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: CardStoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(story: ListStoryItem) {
            binding.apply {
                storyUsername.text = story.name
                storyDesc.text = story.description

                Glide.with(itemView.context)
                    .load(R.drawable.img_default_profile)
                    .apply(RequestOptions().override(100,100))
                    .into(storyAvatar)

                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(storyImage)
            }

            itemView.setOnClickListener {
                // Do Something
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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