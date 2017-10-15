package com.shuttl.assignment.shuttlapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shuttl.assignment.fragment.PeopleFragment;
import com.shuttl.assignment.fragment.PlaceFragment;
import com.shuttl.assignment.fragment.QuoteFragment;
import com.shuttl.assignment.model.FeedItem;

/**
 * Created by l on 10/14/2017.
 */

public class DetailActivity extends AppCompatActivity {


    Fragment itemTypeFragment;
    TextView mDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_detail_view);

        mDescription = (TextView) findViewById(R.id.description_txt);
        FeedItem item = (FeedItem) getIntent().getSerializableExtra("feed_item");

        if (item != null && item.getDescription() != null)
            mDescription.setText(item.getDescription());

        if (item != null && item.getTitle() != null)
            getSupportActionBar().setTitle(item.getTitle());

        if (item.getTitle() != null && item.getTitle().equalsIgnoreCase("People")) {
            PeopleFragment fragment = PeopleFragment.newInstance(item);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
        } else if (item.getTitle() != null && item.getTitle().equalsIgnoreCase("Quote")) {
            QuoteFragment fragment = QuoteFragment.newInstance(item);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
        } else {
            PlaceFragment fragment = PlaceFragment.newInstance(item);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
        }
      /*  Bundle bundle = new Bundle();
        bundle.putString("item_type", item.getTitle());
        fragment.setArguments(bundle);*/
    }


}





