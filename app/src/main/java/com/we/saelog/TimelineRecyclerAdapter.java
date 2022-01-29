package com.we.saelog;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// 저장한 티켓을 목록으로 보여주기 위한 Recycler View Adapter
public class TimelineRecyclerAdapter extends RecyclerView.Adapter<TimelineRecyclerAdapter.ViewHolder> {
    private ArrayList<MyPost> myPostArrayList;

    @NonNull
    @Override
    public TimelineRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(myPostArrayList.get(position));
    }

    public void setMyPostArrayList(List<MyPost> list){
        // DB에 저장된 포스트로 RecyclerView를 구성
        ArrayList<MyPost> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        myPostArrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myPostArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 포스트를 보여줄 TextView를 id로 불러오기
            title = (TextView) itemView.findViewById(R.id.postTitle);
        }

        void onBind(MyPost item){
            title.setText(item.getTitle());                             // 목록에 티켓 제목이 보이게 설정
        }
    }
}