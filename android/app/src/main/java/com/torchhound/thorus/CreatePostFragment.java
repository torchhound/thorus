package com.torchhound.thorus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class CreatePostFragment extends Fragment {

    public static final String CREATEPOSTKEY = "createPostKey";

    private EditText postTitle;
    private EditText postBody;
    private Button uploadImage;
    private Button createOrPost;
    private ImageButton exit;

    public enum createPostEnum {
        CREATE, POST
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_post, container, false);

        postTitle = rootView.findViewById(R.id.post_title);
        postBody = rootView.findViewById(R.id.post_body);
        uploadImage = rootView.findViewById(R.id.upload_image);
        createOrPost = rootView.findViewById(R.id.post_or_create);
        exit = rootView.findViewById(R.id.exit);

        Bundle args = getArguments();

        if (args != null) {
            createPostEnum createPost = (createPostEnum) args.getSerializable(CREATEPOSTKEY);
            assert createPost != null;
            switch (createPost) {
                case CREATE:
                    createOrPost.setText(R.string.create);
                    postTitle.setVisibility(View.VISIBLE);
                    break;
                case POST:
                    createOrPost.setText(R.string.post);
                    break;
            }
        }

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO(jcieslik) browse local images
            }
        });

        createOrPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO(jcieslik) create or post to api
            }
        });

        return rootView;
    }

}
