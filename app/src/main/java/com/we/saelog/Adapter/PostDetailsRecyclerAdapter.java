package com.we.saelog.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;
import com.we.saelog.room.CategoryDAO;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;
import com.we.saelog.room.MyPost;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PostDetailsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MyPost post;
    private MyCategory category;

    CategoryDB categoryDB;

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
                v = inflater.inflate(R.layout.content_detail_text, parent, false);
                return new PostDetailsRecyclerAdapter.textViewHolder(v);
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

    public void setCategoryDB(Context context){
        categoryDB = CategoryDB.getAppDatabase(context);
    }

    public void setPost(MyPost post) {
        this.post = post;
        try {
            category = new CategoryAsyncTask(categoryDB.categoryDAO()).execute(post.getCategory()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            mRatingbar.setRating(Float.valueOf(post.getContent(i)));
        }
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class CategoryAsyncTask extends AsyncTask<Integer, Void, MyCategory> {
        private final CategoryDAO categoryDAO;

        public  CategoryAsyncTask(CategoryDAO categoryDAO){
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected MyCategory doInBackground(Integer... ID) {
            List<MyCategory> categoryList = categoryDAO.findByID(ID[0]);
            try {
                return categoryList.get(0);
            } catch (Exception e) {
                return new MyCategory("","","",0,0);
            }
        }
    }
}
