package com.shuttl.assignment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.shuttl.assignment.app.AppController;
import com.shuttl.assignment.model.FeedItem;
import com.shuttl.assignment.shuttlapp.R;

/**
 * Created by l on 10/14/2017.
 */

public class PlaceFragment extends Fragment {


    String name, imageUrl;
    boolean isLiked;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public static PlaceFragment newInstance(FeedItem feedItem) {
        Bundle bundle = new Bundle();
        bundle.putString("image", feedItem.getImageUrl());
        bundle.putString("name", feedItem.getName());
        bundle.putBoolean("isLiked", feedItem.isLiked());


        PlaceFragment fragment = new PlaceFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public void readBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            imageUrl = bundle.getString("image");
            isLiked = bundle.getBoolean("isLiked");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View
                view = inflater.inflate(R.layout.feed_item_place, null, false);
        TextView mNameTextView = (TextView) view.findViewById(R.id.name);
        final Button mLikeBtn = (Button) view.findViewById(R.id.btn_like);
        NetworkImageView mFeedImageView = (NetworkImageView) view.findViewById(R.id.feedImage1);

        readBundle();

        if (imageUrl != null)
            mFeedImageView.setImageUrl(imageUrl, imageLoader);
        if (name != null)
            mNameTextView.setText("From " + name);

        if (!isLiked)
            mLikeBtn.setText("LIKE");
        else
            mLikeBtn.setText("DISLIKE");

        mLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLiked) {
                    //make api call and send data to server with updated flag
                    isLiked = true;
                    mLikeBtn.setText("DISLIKE");
                } else {
                    //make api call and send data to server with updated flag
                    isLiked = false;
                    mLikeBtn.setText("LIKE");
                }
            }
        });


        return view;
    }


}