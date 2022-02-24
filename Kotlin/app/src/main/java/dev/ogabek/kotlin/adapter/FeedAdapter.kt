package dev.ogabek.kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import dev.ogabek.kotlin.R
import dev.ogabek.kotlin.model.Feed

class FeedAdapter(var context: Context, private var feeds: List<Feed>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER_VIEW = 0
    private val TYPE_STORY_VIEW = 1
    private val TYPE_POST_VIEW = 2

    override fun getItemViewType(position: Int): Int {
        val feed: Feed = feeds[position]
        return if (feed.isHeader) {
            TYPE_HEADER_VIEW
        } else if (feed.stories.size > 0) {
            TYPE_STORY_VIEW
        } else {
            TYPE_POST_VIEW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER_VIEW -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feed_header, parent, false)
                FeedHeaderViewHolder(view)
            }
            TYPE_STORY_VIEW -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_feed_story, parent, false)
                FeedStoryViewHolder(context, view)
            }
            else -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_feed_post, parent, false)
                FeedPostViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val feed: Feed = feeds[position]
        if (holder is FeedStoryViewHolder) {
            val recyclerView = holder.recyclerView
            recyclerView.adapter = StoryAdapter(feed.stories)
        }
        if (holder is FeedPostViewHolder) {
            holder.iv_post_photo.setImageResource(feed.post!!.photo)
            holder.iv_post_profile.setImageResource(feed.post!!.profile)
            holder.tv_post_full_name.text = feed.post!!.fullName
        }
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    private class FeedHeaderViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)
    private class FeedPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_post_profile: ShapeableImageView = view.findViewById(R.id.iv_post_profile)
        var tv_post_full_name: TextView = view.findViewById(R.id.tv_post_full_name)
        var iv_post_photo: ImageView = view.findViewById(R.id.iv_post_photo)

    }

    private class FeedStoryViewHolder(context: Context?, view: View) :
        RecyclerView.ViewHolder(view) {
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewStory)

        init {
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}