package com.torchhound.thorus;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import static com.torchhound.thorus.CreatePostFragment.CREATEPOSTKEY;

public class BoardActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView emptyTextView;
    private FrameLayout threadLayout;
    private FloatingActionButton createThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = findViewById(R.id.threadsRecyclerView);
        emptyTextView = findViewById(android.R.id.empty);
        threadLayout = findViewById(R.id.thread_frame);
        createThread = findViewById(R.id.create_fab);

        createThread.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CreatePostFragment fragment = new CreatePostFragment();
                Bundle args = new Bundle();
                args.putSerializable(CREATEPOSTKEY, CreatePostFragment.createPostEnum.CREATE);
                fragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.thread_frame, fragment);
                transaction.commit();
            }
        });

        emptyTextView.setVisibility(View.VISIBLE);
    }
}
