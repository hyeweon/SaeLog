package com.we.saelog.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.PostDetailsActivity;
import com.we.saelog.R;
import com.we.saelog.room.MyCategory;
import com.we.saelog.room.MyPost;

import java.util.List;

public class CategoryPostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyCategory category;
    private List<MyPost> posts;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        Integer layout;

        switch (category.getType()) {
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type1, parent, false);
                return new ViewHolder1(v);
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type2, parent, false);
                return new ViewHolder4(v);
            case 3:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type3, parent, false);
                return new ViewHolder4(v);
            case 4:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type4, parent, false);
                return new ViewHolder2(v);
            case 5:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type5, parent, false);
                return new ViewHolder3(v);
            case 6:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type6, parent, false);
                return new ViewHolder2(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type5, parent, false);
                return new ViewHolder3(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CategoryPostRecyclerAdapter.ViewHolder1)
        {
            ((CategoryPostRecyclerAdapter.ViewHolder1)holder).onBind(posts.get(position));
        }
        else if(holder instanceof CategoryPostRecyclerAdapter.ViewHolder2)
        {
            ((CategoryPostRecyclerAdapter.ViewHolder2)holder).onBind(posts.get(position));
        }
        else if(holder instanceof CategoryPostRecyclerAdapter.ViewHolder3)
        {
            ((CategoryPostRecyclerAdapter.ViewHolder3)holder).onBind(posts.get(position));
        }
        else if(holder instanceof CategoryPostRecyclerAdapter.ViewHolder4)
        {
            ((CategoryPostRecyclerAdapter.ViewHolder4)holder).onBind(posts.get(position));
        }
    }

    public void setCategory(MyCategory category) {
        this.category = category;
    }

    public void setPosts(List<MyPost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Thumbnail
    public class ViewHolder1 extends RecyclerView.ViewHolder {

        public ImageView mThumbnail;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            mThumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "포스트 상세보기를 준비 중이에요", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    MyPost item = posts.get(position);
                    startPostDetailsActivity(v, position, item);
                }
            });
        }

        void onBind(MyPost item){
            String strThumbnail = item.getThumbnail();
            if(strThumbnail != null && strThumbnail != ""){
                Bitmap bitmapThumbnail = StringToBitmap(strThumbnail);
                mThumbnail.setImageBitmap(bitmapThumbnail);
            }
        }
    }

    // Title, Thumbnail
    public class ViewHolder2 extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public ImageView mThumbnail;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            mThumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "포스트 상세보기를 준비 중이에요", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    MyPost item = posts.get(position);
                    startPostDetailsActivity(v, position, item);
                }
            });
        }

        void onBind(MyPost item){
            mTitle.setText(item.getTitle());

            String strThumbnail = item.getThumbnail();
            if(strThumbnail != null && strThumbnail != ""){
                Bitmap bitmapThumbnail = StringToBitmap(strThumbnail);
                mThumbnail.setImageBitmap(bitmapThumbnail);
            }
        }
    }

    // Title, Content1
    public class ViewHolder3 extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public TextView mContent;

        public ViewHolder3(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            mContent = itemView.findViewById(R.id.content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "포스트 상세보기를 준비 중이에요", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    MyPost item = posts.get(position);
                    startPostDetailsActivity(v, position, item);
                }
            });
        }

        void onBind(MyPost item){
            mTitle.setText(item.getTitle());
            mContent.setText(item.getContent(1));
        }
    }

    // Title, Thumbnail, Content1
    public class ViewHolder4 extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public ImageView mThumbnail;
        public TextView mContent;

        public ViewHolder4(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            mThumbnail = itemView.findViewById(R.id.thumbnail);
            mContent = itemView.findViewById(R.id.content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "포스트 상세보기를 준비 중이에요", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    MyPost item = posts.get(position);
                    startPostDetailsActivity(v, position, item);
                }
            });
        }

        void onBind(MyPost item){
            mTitle.setText(item.getTitle());
            mContent.setText(item.getContent(1));

            String strThumbnail = item.getThumbnail();
            if(strThumbnail != null && strThumbnail != ""){
                Bitmap bitmapThumbnail = StringToBitmap(strThumbnail);
                mThumbnail.setImageBitmap(bitmapThumbnail);
            }
        }
    }

    public void startPostDetailsActivity(View v, int position, MyPost item){
        Intent intent;
        if (position != RecyclerView.NO_POSITION) {         // 목록의 포스트를 누를 경우 실행
            String itemTitle = item.getTitle();             // 포스트의 제목 정보 가져오기
            int itemID = item.getPostID();                  // 포스트 삭제를 위한 ID 가져오기

            // Intent로 해당 포스트의 정보 교환
            intent = new Intent(v.getContext(), PostDetailsActivity.class);
            intent.putExtra("post", item);
            intent.putExtra("title", itemTitle);
            intent.putExtra("ID", itemID);
            v.getContext().startActivity(intent);           // Intent 호출
        }
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
