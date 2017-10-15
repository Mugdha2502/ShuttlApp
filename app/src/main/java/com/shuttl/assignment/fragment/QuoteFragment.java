package com.shuttl.assignment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.shuttl.assignment.model.FeedItem;
import com.shuttl.assignment.shuttlapp.R;

/**
 * Created by l on 10/14/2017.
 */

public class QuoteFragment extends Fragment {

    String content, name;
    boolean isLiked;

    public static QuoteFragment newInstance(FeedItem feedItem) {
        Bundle bundle = new Bundle();
        bundle.putString("text", feedItem.getText());
        bundle.putString("name", feedItem.getName());
        bundle.putBoolean("isLiked", feedItem.isLiked());


        QuoteFragment fragment = new QuoteFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public void readBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getString("text");
            name = bundle.getString("name");
            isLiked = bundle.getBoolean("isLiked");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.feed_item_quote, null, false);
        TextView mNameTextView = (TextView) view.findViewById(R.id.name);
        TextView mContentTextView = (TextView) view.findViewById(R.id.text);
        final Button mLikeBtn = (Button) view.findViewById(R.id.btn_like);

        readBundle();


        if (name != null)
            mNameTextView.setText("From " + name);
        if (content != null)
            mContentTextView.setText(content);
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