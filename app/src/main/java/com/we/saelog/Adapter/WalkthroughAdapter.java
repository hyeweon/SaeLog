package com.we.saelog.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;

import java.util.ArrayList;

public class WalkthroughAdapter extends RecyclerView.Adapter<WalkthroughAdapter.ViewHolder>{
    private ArrayList<String> walkthroughArrayList;

    public WalkthroughAdapter(ArrayList<String> walkthroughArrayList) {
        this.walkthroughArrayList = walkthroughArrayList;
    }

    @NonNull
    @Override
    public WalkthroughAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_walkthrough, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(walkthroughArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return walkthroughArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView introduction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            introduction = (TextView) itemView.findViewById(R.id.introduction);
        }

        void onBind(String item){
            introduction.setText(item);
        }
    }
}
