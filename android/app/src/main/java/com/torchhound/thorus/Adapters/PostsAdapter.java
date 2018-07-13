package com.torchhound.thorus.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.torchhound.thorus.Models.Post;
import com.torchhound.thorus.R;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Post> posts = new ArrayList<>();
    private final OnItemClick clickListener;
    private final Context context;

    public interface OnItemClick {
        void onItemClick(View v, int position);
    }

    public PostsAdapter(Context context, ArrayList<Post> posts, OnItemClick clickListener) {
        this.posts= posts;
        this.clickListener = clickListener;
        this.context = context;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClick clickListener;

        private TextView postId;
        private TextView postBody;
        private ImageView postImage;

        public ViewHolder(View itemView, OnItemClick clickListener) {
            super(itemView);

            this.clickListener = clickListener;
            itemView.setOnClickListener(this);

            postId = itemView.findViewById(R.id.post_id);
            postBody = itemView.findViewById(R.id.post_body);
            postImage = itemView.findViewById(R.id.post_image);
        }

        void loadPostItem(Context context, Post post) {
            postId.setText(post.getPostId());
            postBody.setText(post.getPostBody());

            Picasso.with(context).load(post.getImageUrl()).into(postImage);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.post_row, parent, false);
        return new ViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Post post = posts.get(position);
        viewHolder.loadPostItem(context, post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
