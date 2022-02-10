package com.we.saelog.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;

import java.util.ArrayList;

public class NewCategoryContentsAdapter extends RecyclerView.Adapter<NewCategoryContentsAdapter.ViewHolder> {
    private ArrayList<Integer> contentsArrayList;

    @NonNull
    @Override
    public NewCategoryContentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_category_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewCategoryContentsAdapter.ViewHolder holder, int position) {
        holder.onBind(contentsArrayList.get(position));
    }

    public void setContentsArrayList(ArrayList<Integer> contentsArrayList) {
        this.contentsArrayList = contentsArrayList;
    }

    @Override
    public int getItemCount() {
        return contentsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Integer integer) {
        }
    }
}
