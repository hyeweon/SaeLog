package com.we.saelog.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;
import com.we.saelog.room.MyCategory;

import java.util.ArrayList;
import java.util.List;

public class MyPageRecyclerAdapter extends RecyclerView.Adapter<MyPageRecyclerAdapter.ViewHolder> {
    private ArrayList<MyCategory> myCategoryArrayList;

    @NonNull
    @Override
    public MyPageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_page_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPageRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(myCategoryArrayList.get(position));
    }

    public void setMyCategoryArrayList(List<MyCategory> list){
        // DB에 저장된 카테고리로 RecyclerView를 구성
        ArrayList<MyCategory> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        myCategoryArrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myCategoryArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 포스트를 보여줄 TextView를 id로 불러오기
            title = (TextView) itemView.findViewById(R.id.categoryTitle);
        }

        void onBind(MyCategory item){
            title.setText(item.getTitle());                             // 목록에 티켓 제목이 보이게 설정
        }
    }
}
