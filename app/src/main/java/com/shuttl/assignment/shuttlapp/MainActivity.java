package com.shuttl.assignment.shuttlapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.Cache;
import com.shuttl.assignment.adapter.ListViewAdapter;
import com.shuttl.assignment.app.AppController;
import com.shuttl.assignment.model.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by l on 10/14/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private ListViewAdapter listAdapter;
    private List<FeedItem> feedItems;
    private LinkedHashMap<String, List<FeedItem>> sortedFeed;
    private String URL_FEED = "https://api.androidhive.info/feed/feed.json";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        sortedFeed = new LinkedHashMap<>();

        listAdapter = new ListViewAdapter(this, sortedFeed);
        listView.setAdapter(listAdapter);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
//                    parseJsonFeed(new JSONObject(data));
                    parseJsonFeed(new JSONObject(loadJSONFromAsset()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {

            try {
                parseJsonFeed(new JSONObject(loadJSONFromAsset()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // #in actual code we will make volley request to get json
            /*
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);*/
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("sample.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                if (feedObj.has("imageUrl")) {
                    String image = feedObj.isNull("imageUrl") ? null : feedObj
                            .getString("imageUrl");
                    item.setImageUrl(image);
                }
                item.setTitle(feedObj.getString("title"));
                if (feedObj.has("text"))
                    item.setText(feedObj.getString("text"));
                item.setTime(feedObj.getString("time"));
                item.setDescription(feedObj.getString("description"));

                feedItems.add(item);
            }

            sortData(feedItems);

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sortData(List<FeedItem> feedItems) {

        for (int i = 0; i < feedItems.size(); i++) {
            long feed_time = Long.parseLong(feedItems.get(i).getTime());
            String date = convertTimeStamptoDate(feed_time);
            if (!sortedFeed.containsKey(date)) {
                List<FeedItem> list = new ArrayList<FeedItem>();
                list.add(feedItems.get(i));
                sortedFeed.put(date, list);
            } else {
                sortedFeed.get(date).add(feedItems.get(i));
            }
        }

    }

    String convertTimeStamptoDate(long ms) {
        long millisInDay = 60 * 60 * 24 * 1000;
        long dateOnly = (ms / millisInDay) * millisInDay;
        Date feed_date = new Date(dateOnly);
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s = formatter.format(feed_date);
        return s;
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
}