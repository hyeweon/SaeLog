package com.we.saelog.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.PostDetailsActivity;
import com.we.saelog.R;
import com.we.saelog.room.CategoryDAO;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;
import com.we.saelog.room.MyPost;
import com.we.saelog.room.PostDAO;
import com.we.saelog.room.PostDB;

import java.util.ArrayList;
import java.util.List;

// 저장한 티켓을 목록으로 보여주기 위한 Recycler View Adapter
public class TimelineRecyclerAdapter extends RecyclerView.Adapter<TimelineRecyclerAdapter.ViewHolder> {

    private ArrayList<MyPost> myPostArrayList;

    PostDB db;
    CategoryDB categoryDB;

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
        ImageView mThumbnail;
        TextView title, mCategory, mDate;
        ImageButton btnHeart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // DB 호출
            db = PostDB.getAppDatabase(itemView.getContext());
            categoryDB = CategoryDB.getAppDatabase(itemView.getContext());

            mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            // 포스트를 보여줄 TextView를 id로 불러오기
            title = (TextView) itemView.findViewById(R.id.postTitle);
            mCategory = (TextView) itemView.findViewById(R.id.postCategory);
            mDate = (TextView) itemView.findViewById(R.id.postDate);
            // 마음글 여부를 나타낼 ImageButton을 id로 불러오기
            btnHeart = itemView.findViewById(R.id.btnHeart);

            // 하트를 클릭하면 마음글로 등록되도록 OnClickListener 설정
            btnHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    MyPost item = myPostArrayList.get(position) ;  // 어떤 포스트를 눌렀는지

                    boolean itemIsHearted = item.getHearted();

                    if(itemIsHearted==true){
                        btnHeart.setImageResource(R.drawable.icon_heart);
                        itemIsHearted = false;
                    }
                    else{
                        btnHeart.setImageResource(R.drawable.icon_fullheart);
                        itemIsHearted = true;
                    }

                    item.setHearted(itemIsHearted);
                    // DB에 update
                    new UpdateAsyncTask(db.postDAO()).execute(item);
                }
            });

            // 클릭하면 각 포스트의 상세 보기가 가능하도록 OnClickListener 설정
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {         // 목록의 포스트를 누를 경우 실행
                        MyPost item = myPostArrayList.get(position) ;   // 어떤 포스트를 눌렀는지
                        String itemTitle = item.getTitle();             // 포스트의 제목 정보 가져오기
                        int itemID = item.getPostID();                  // 포스트 삭제를 위한 ID 가져오기

                        // Intent로 해당 포스트의 정보 교환
                        intent = new Intent(v.getContext(), PostDetailsActivity.class);
                        intent.putExtra("post", item);
                        intent.putExtra("title",itemTitle);
                        intent.putExtra("ID",itemID);
                        v.getContext().startActivity(intent);           // Intent 호출
                    }
                }
            });
        }

        void onBind(MyPost item){
            title.setText(item.getTitle());
            mDate.setText(item.getDate());

            // Thumbnail
            String strThumbnail = item.getThumbnail();
            if(strThumbnail != null && strThumbnail != "") {
                Bitmap bitmapThumbnail = StringToBitmap(strThumbnail);
                mThumbnail.setImageBitmap(bitmapThumbnail);
            }

            try {
                String categoryTitle = new CategoryAsyncTask(categoryDB.categoryDAO()).execute(item.getCategory()).get();
                mCategory.setText(categoryTitle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(item.getHearted()==true){
                btnHeart.setImageResource(R.drawable.icon_fullheart);
            }
            else {
                btnHeart.setImageResource(R.drawable.icon_heart);
            }
        }
    }

    // from https://stickode.com/detail.html?no=1297
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

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class CategoryAsyncTask extends AsyncTask<Integer, Void, String> {
        private final CategoryDAO categoryDAO;

        public  CategoryAsyncTask(CategoryDAO categoryDAO){
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected String doInBackground(Integer... ID) {
            List<MyCategory> categoryList = categoryDAO.findByID(ID[0]);
            try {
                return categoryList.get(0).getTitle();
            } catch (Exception e) {
                return "";
            }
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<MyPost, Void, Void> {
        private final PostDAO postDAO;

        public  UpdateAsyncTask(PostDAO postDAO){
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(MyPost... myPost) {
            postDAO.update(myPost[0]); // DB에 새로운 포스트 추가
            return null;
        }
    }
}