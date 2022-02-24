package com.we.saelog.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;
import com.we.saelog.room.MyCategory;

public class NewPostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyCategory category;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE);

        switch (viewType){
            case 1:
                v = inflater.inflate(R.layout.content_text_short, parent, false);
                return new shortTextViewHolder(v);
            case 2:
                v = inflater.inflate(R.layout.content_text_long, parent, false);
                return new longTextViewHolder(v);
            case 3:
                v = inflater.inflate(R.layout.content_checkbox, parent, false);
                return new checkboxViewholder(v);
            case 4:
                v = inflater.inflate(R.layout.content_ratingbar, parent, false);
                return new ratingbarViewholder(v);
            case 5:
                v = inflater.inflate(R.layout.content_text_short, parent, false);
                return new shortTextViewHolder(v);
            default:
                v = inflater.inflate(R.layout.content_text_short, parent, false);
                return new shortTextViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof shortTextViewHolder)
        {
            ((shortTextViewHolder)holder).onBind(category.getContentTitle(position));
        }
        else if(holder instanceof longTextViewHolder)
        {
            ((longTextViewHolder)holder).onBind(category.getContentTitle(position));
        }
        else if(holder instanceof checkboxViewholder)
        {
            ((checkboxViewholder)holder).onBind(category.getContentTitle(position));
        }
        else if(holder instanceof ratingbarViewholder)
        {
            ((ratingbarViewholder)holder).onBind(category.getContentTitle(position));
        }
    }

    @Override
    public int getItemCount() {
        return category.getContentsNum();
    }

    @Override
    public int getItemViewType(int position) {
        return category.getContentType(position + 1);
    }

    public class shortTextViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;

        public shortTextViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(String item) {
            mTitle.setText(item);
        }
    }
    public class longTextViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;

        public longTextViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(String item) {
            mTitle.setText(item);
        }
    }

    public class checkboxViewholder extends RecyclerView.ViewHolder {
        public TextView mTitle;

        public checkboxViewholder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(String item) {
            mTitle.setText(item);
        }
    }

    public class ratingbarViewholder extends RecyclerView.ViewHolder {
        public TextView mTitle;

        public ratingbarViewholder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(String item) {
            mTitle.setText(item);
        }
    }
}
