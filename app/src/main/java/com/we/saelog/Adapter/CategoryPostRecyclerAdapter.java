package com.we.saelog.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;
import com.we.saelog.room.MyCategory;
import com.we.saelog.room.MyPost;

import java.util.ArrayList;

public class CategoryPostRecyclerAdapter extends RecyclerView.Adapter<CategoryPostRecyclerAdapter.ViewHolder> {
    private MyCategory category;
    private ArrayList<MyPost> posts;

    @NonNull
    @Override
    public CategoryPostRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        Integer layout;

        switch (category.getType()) {
            case 0:
                layout = R.layout.item_type1;
            case 1:
                layout = R.layout.item_type1;
                break;
            case 2:
                layout = R.layout.item_type1;
                break;
            case 3:
                layout = R.layout.item_type3;
                break;
            case 4:
                layout = R.layout.item_type1;
            case 5:
                layout = R.layout.item_type1;
            default:
                layout = R.layout.item_type1;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPostRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(posts.get(position));
    }

    public void setCategory(MyCategory category) {
        this.category = category;
    }

    public void setMyPostArrayList(ArrayList<MyPost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void onBind(MyPost item){

        }
    }
}
