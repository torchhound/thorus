package com.torchhound.thorus.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.torchhound.thorus.Models.Thread;
import com.torchhound.thorus.R;

import java.util.ArrayList;

public class ThreadsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Thread> threads = new ArrayList<>();
    private final OnItemClick clickListener;

    public interface OnItemClick {
        void onItemClick(View v, int position);
    }

    public ThreadsAdapter(ArrayList<Thread> threads, OnItemClick clickListener) {
        this.threads = threads;
        this.clickListener = clickListener;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClick clickListener;

        private TextView threadId;
        private TextView threadTitle;

        public ViewHolder(View itemView, OnItemClick clickListener) {
            super(itemView);

            this.clickListener = clickListener;
            itemView.setOnClickListener(this);

            threadId = itemView.findViewById(R.id.thread_id);
            threadTitle = itemView.findViewById(R.id.thread_title);
        }

        void loadThread(Thread thread) {
            threadId.setText(thread.getThreadId());
            threadTitle.setText(thread.getThreadTitle());
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
        View itemView = inflater.inflate(R.layout.thread_row, parent, false);
        return new ViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Thread thread = threads.get(position);
        viewHolder.loadThread(thread);
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }
}
