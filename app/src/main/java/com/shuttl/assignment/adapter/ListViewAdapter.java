package com.shuttl.assignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuttl.assignment.model.FeedItem;
import com.shuttl.assignment.shuttlapp.DetailActivity;
import com.shuttl.assignment.shuttlapp.R;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by l on 10/14/2017.
 */

public class ListViewAdapter extends BaseAdapter implements MultipleViewTypesAdapter.OnItemClickListener {
    private Activity activity;
    private LayoutInflater inflater;
    private LinkedHashMap<String, List<FeedItem>> feedItems;
    MultipleViewTypesAdapter recyclerViewAdapter;

    public ListViewAdapter(Activity activity, LinkedHashMap<String, List<FeedItem>> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_view_item, null);

        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(feedItems.keySet().toArray()[position].toString());


        RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerViewAdapter = new MultipleViewTypesAdapter(activity, feedItems.get(date.getText().toString()), this);
        recyclerView.setAdapter(recyclerViewAdapter);

        return convertView;
    }

    @Override
    public void onItemClick(FeedItem item) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("feed_item", item);
        activity.startActivity(intent);

    }
}