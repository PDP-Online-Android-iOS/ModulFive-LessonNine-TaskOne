package dev.ogabek.java.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import dev.ogabek.java.R;
import dev.ogabek.java.model.Feed;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Feed> feeds;

    private static final int TYPE_HEADER_VIEW = 0;
    private static final int TYPE_STORY_VIEW = 1;
    private static final int TYPE_POST_VIEW = 2;

    public FeedAdapter(Context context, List<Feed> feeds) {
        this.context = context;
        this.feeds = feeds;
    }

    @Override
    public int getItemViewType(int position) {
        Feed feed = feeds.get(position);
        if (feed.isHeader()) {
            return TYPE_HEADER_VIEW;
        } else if (feed.getStories().size() > 0) {
            return TYPE_STORY_VIEW;
        } else {
            return TYPE_POST_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_header, parent, false);
            return new FeedHeaderViewHolder(view);
        } else if (viewType == TYPE_STORY_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_story, parent, false);
            return new FeedStoryViewHolder(context, view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_post, parent, false);
            return new FeedPostViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Feed feed = feeds.get(position);

        if (holder instanceof FeedStoryViewHolder) {
            RecyclerView recyclerView = ((FeedStoryViewHolder) holder).recyclerView;
            recyclerView.setAdapter(new StoryAdapter(feed.getStories()));
        }

        if (holder instanceof FeedPostViewHolder) {
            ((FeedPostViewHolder) holder).iv_post_photo.setImageResource(feed.getPost().getPhoto());
            ((FeedPostViewHolder) holder).iv_post_profile.setImageResource(feed.getPost().getProfile());
            ((FeedPostViewHolder) holder).tv_post_full_name.setText(feed.getPost().getFullName());
        }

    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    private static class FeedHeaderViewHolder extends RecyclerView.ViewHolder {
        public FeedHeaderViewHolder(View view) {
            super(view);
        }
    }

    private static class FeedPostViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView iv_post_profile;
        TextView tv_post_full_name;
        ImageView iv_post_photo;

        public FeedPostViewHolder(View view) {
            super(view);

            iv_post_profile = view.findViewById(R.id.iv_post_profile);
            tv_post_full_name = view.findViewById(R.id.tv_post_full_name);
            iv_post_photo = view.findViewById(R.id.iv_post_photo);

        }
    }

    private static class FeedStoryViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        public FeedStoryViewHolder(Context context, View view) {
            super(view);

            recyclerView = view.findViewById(R.id.recyclerViewStory);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        }
    }
}
