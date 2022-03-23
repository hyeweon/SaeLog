package com.we.saelog.Adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;
import com.we.saelog.room.MyCategory;
import com.we.saelog.room.MyPost;

public class PostDetailsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MyPost post;
    private MyCategory category;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE);

        switch (viewType){
            case 1:
                v = inflater.inflate(R.layout.content_detail_text, parent, false);
                return new PostDetailsRecyclerAdapter.textViewHolder(v);
            case 2:
                v = inflater.inflate(R.layout.content_detail_text, parent, false);
                return new PostDetailsRecyclerAdapter.textViewHolder(v);
            case 3:
                v = inflater.inflate(R.layout.content_detail_checkbox, parent, false);
                return new PostDetailsRecyclerAdapter.checkboxViewholder(v);
            case 4:
                v = inflater.inflate(R.layout.content_detail_ratingbar, parent, false);
                return new PostDetailsRecyclerAdapter.ratingbarViewholder(v);
            case 5:
                v = inflater.inflate(R.layout.content_detail_image, parent, false);
                return new PostDetailsRecyclerAdapter.imageViewholder(v);
            default:
                v = inflater.inflate(R.layout.content_detail_text, parent, false);
                return new PostDetailsRecyclerAdapter.textViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PostDetailsRecyclerAdapter.textViewHolder)
        {
            ((PostDetailsRecyclerAdapter.textViewHolder)holder).onBind(position + 1);
        }
        else if(holder instanceof PostDetailsRecyclerAdapter.checkboxViewholder)
        {
            ((PostDetailsRecyclerAdapter.checkboxViewholder)holder).onBind(position + 1);
        }
        else if(holder instanceof PostDetailsRecyclerAdapter.ratingbarViewholder)
        {
            ((PostDetailsRecyclerAdapter.ratingbarViewholder)holder).onBind(position + 1);
        }
    }

    public void setPost(MyPost post) {
        this.post = post;
    }

    public void setCategory(MyCategory category) {
        this.category = category;
    }

    @Override
    public int getItemCount() {
        return category.getContentsNum();
    }

    @Override
    public int getItemViewType(int position) {
        return category.getContentType(position + 1);
    }

    public class textViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mText;

        public textViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.contentTitle);
            mText = itemView.findViewById(R.id.text);
        }

        public void onBind(int i) {
            mTitle.setText(category.getContentTitle(i));
            mText.setText(post.getContent(i));
        }
    }

    public class checkboxViewholder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public CheckBox mCheckbox;

        public checkboxViewholder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.contentTitle);
            mCheckbox = itemView.findViewById(R.id.checkbox);
        }

        public void onBind(int i) {
            mTitle.setText(category.getContentTitle(i));
            if(post.getContent(i).equals("true")) mCheckbox.setChecked(true);
        }
    }

    public class ratingbarViewholder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public RatingBar mRatingbar;

        public ratingbarViewholder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.contentTitle);
            mRatingbar = itemView.findViewById(R.id.ratingbar);
        }

        public void onBind(int i) {
            mTitle.setText(category.getContentTitle(i));
            mRatingbar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(category.getTheme())));
            mRatingbar.setRating(Float.valueOf(post.getContent(i)));
        }
    }

    public class imageViewholder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public RatingBar mRatingbar;

        public imageViewholder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.contentTitle);
            mRatingbar = itemView.findViewById(R.id.ratingbar);
        }

        public void onBind(int i) {
            mTitle.setText(category.getContentTitle(i));
            mRatingbar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(category.getTheme())));
            mRatingbar.setRating(Float.valueOf(post.getContent(i)));
        }
    }
}
