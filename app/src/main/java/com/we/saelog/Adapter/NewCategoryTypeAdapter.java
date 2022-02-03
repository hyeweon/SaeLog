package com.we.saelog.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;

import java.util.ArrayList;

public class NewCategoryTypeAdapter extends RecyclerView.Adapter<NewCategoryTypeAdapter.ViewHolder> {
    private ArrayList<Drawable> categoryTypeArrayList;

    public NewCategoryTypeAdapter(ArrayList<Drawable> categoryTypeArrayList) {
        this.categoryTypeArrayList = categoryTypeArrayList;
    }

    @NonNull
    @Override
    public NewCategoryTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_category_type, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(categoryTypeArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryTypeArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView preview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preview = (ImageView) itemView.findViewById(R.id.preview);
        }

        void onBind(Drawable item){
            preview.setImageDrawable(item);
        }
    }
}
