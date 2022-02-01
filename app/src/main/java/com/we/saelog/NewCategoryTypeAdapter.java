package com.we.saelog;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewCategoryTypeAdapter extends RecyclerView.Adapter<NewCategoryTypeAdapter.ViewHolder> {
    private ArrayList<Drawable> categoryTypeArrayList;

    NewCategoryTypeAdapter(ArrayList<Drawable> data) {
        this.categoryTypeArrayList = data;
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
            // 포스트를 보여줄 TextView를 id로 불러오기
            preview = (ImageView) itemView.findViewById(R.id.preview);
        }

        void onBind(Drawable item){
            preview.setImageDrawable(item);
        }
    }
}
