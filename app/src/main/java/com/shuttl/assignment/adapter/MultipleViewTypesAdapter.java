package com.shuttl.assignment.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * Created by l on 10/14/2017.
 */

public class MultipleViewTypesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    private OnItemClickListener listener;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private static final int VIEW_TYPE_PEOPLE = 0;
    private static final int VIEW_TYPE_QUOTE = 1;
    private static final int VIEW_TYPE_PLACE = 2;

    public interface OnItemClickListener {
        void onItemClick(FeedItem item);
    }


    public MultipleViewTypesAdapter(Activity activity, List<FeedItem> feedItems, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.feedItems = feedItems;
        this.listener = onItemClickListener;
    }

    class ViewHolderFirst extends RecyclerView.ViewHolder {

        final TextView content;
        final TextView name;
        final Button like_btn;

        final NetworkImageView profilePic;

        public ViewHolderFirst(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.text);
            name = (TextView) itemView.findViewById(R.id.name);
            like_btn = (Button) itemView.findViewById(R.id.btn_like);
            profilePic = (NetworkImageView) itemView
                    .findViewById(R.id.profilePic);

        }
    }

    class ViewHolderSecond extends RecyclerView.ViewHolder {
        final TextView content;
        final TextView name;
        final Button like_btn;


        public ViewHolderSecond(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.text);
            name = (TextView) itemView.findViewById(R.id.name);
            like_btn = (Button) itemView.findViewById(R.id.btn_like);

        }
    }

    class ViewHolderThird extends RecyclerView.ViewHolder {
        final NetworkImageView image;
        final TextView name;
        final Button like_btn;


        public ViewHolderThird(View itemView) {
            super(itemView);
            image = (NetworkImageView) itemView.findViewById(R.id.feedImage1);
            name = (TextView) itemView.findViewById(R.id.name);
            like_btn = (Button) itemView.findViewById(R.id.btn_like);


        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (feedItems.get(position).getTitle()) {
            case "People":
                return VIEW_TYPE_PEOPLE;
            case "Quote":
                return VIEW_TYPE_QUOTE;
            case "Place":
                return VIEW_TYPE_PLACE;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        switch (viewType) {
            case VIEW_TYPE_PEOPLE:
                View view_people = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_people, parent, false);
                return new ViewHolderFirst(view_people);
            case VIEW_TYPE_QUOTE:
                View view_quote = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_quote, parent, false);
                return new ViewHolderSecond(view_quote);
            case VIEW_TYPE_PLACE:
                View view_place = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_place, parent, false);
                return new ViewHolderThird(view_place);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(feedItems.get(position));
            }
        });
        switch (getItemViewType(position)) {
            case VIEW_TYPE_PEOPLE:
                final ViewHolderFirst viewHolderFirst = (ViewHolderFirst) holder;
                viewHolderFirst.content.setText(feedItems.get(position).getText());
                viewHolderFirst.name.setText("From " + feedItems.get(position).getName());
                if (feedItems.get(position).getImageUrl() != null) {
                    viewHolderFirst.profilePic.setImageUrl(feedItems.get(position).getImageUrl(), imageLoader);
                    viewHolderFirst.profilePic.setVisibility(View.VISIBLE);
                } else {
                    viewHolderFirst.profilePic.setVisibility(View.GONE);
                }
                viewHolderFirst.like_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!feedItems.get(position).isLiked()) {
                            feedItems.get(position).setLiked(true);
                            viewHolderFirst.like_btn.setText("DISLIKE");
                        } else {
                            feedItems.get(position).setLiked(false);
                            viewHolderFirst.like_btn.setText("LIKE");
                        }
                        // make api call (jsonobject request volley post method and pass isLiked boolean flag as parameter)
                    }
                });

                break;
            case VIEW_TYPE_QUOTE:
                final ViewHolderSecond viewHolderSecond = (ViewHolderSecond) holder;
                viewHolderSecond.content.setText(feedItems.get(position).getText());
                viewHolderSecond.name.setText("From " + feedItems.get(position).getName());
                viewHolderSecond.like_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!feedItems.get(position).isLiked()) {
                            feedItems.get(position).setLiked(true);
                            viewHolderSecond.like_btn.setText("DISLIKE");
                        } else {
                            feedItems.get(position).setLiked(false);
                            viewHolderSecond.like_btn.setText("LIKE");
                        }
                    }
                });

                break;
            case VIEW_TYPE_PLACE:
                final ViewHolderThird viewHolderThird = (ViewHolderThird) holder;
                viewHolderThird.name.setText("From " + feedItems.get(position).getName());
                if (feedItems.get(position).getImageUrl() != null) {
                    viewHolderThird.image.setImageUrl(feedItems.get(position).getImageUrl(), imageLoader);
                    viewHolderThird.image.setVisibility(View.VISIBLE);
                } else {
                    viewHolderThird.image.setVisibility(View.GONE);
                }
                viewHolderThird.like_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!feedItems.get(position).isLiked()) {
                            feedItems.get(position).setLiked(true);
                            viewHolderThird.like_btn.setText("DISLIKE");
                        } else {
                            feedItems.get(position).setLiked(false);
                            viewHolderThird.like_btn.setText("LIKE");
                        }
                    }
                });


                break;

        }
    }
}
