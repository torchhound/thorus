package com.torchhound.thorus;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.torchhound.thorus.CreatePostFragment.CREATEPOSTKEY;

public class ThreadFragment extends Fragment {

    private FloatingActionButton postReply;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thread, container, false);

        postReply = rootView.findViewById(R.id.post_fab);

        postReply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CreatePostFragment fragment = new CreatePostFragment();
                Bundle args = new Bundle();
                args.putSerializable(CREATEPOSTKEY, CreatePostFragment.createPostEnum.POST);
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.thread_frame, fragment);
                transaction.commit();
            }
        });

        return rootView;
    }

}
