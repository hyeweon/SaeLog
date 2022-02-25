package com.we.saelog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.Adapter.CategoryPostRecyclerAdapter;
import com.we.saelog.room.MyCategory;
import com.we.saelog.room.MyPost;
import com.we.saelog.room.PostDAO;
import com.we.saelog.room.PostDB;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryPostFragment newInstance(String param1, String param2) {
        CategoryPostFragment fragment = new CategoryPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    PostDB db;
    CategoryPostRecyclerAdapter mRecyclerAdapter;

    private TextView mCategoryTitle;

    private MyCategory category;
    private ArrayList<MyPost> posts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // DB 호출
        db = PostDB.getAppDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view에 inflate
        View v = inflater.inflate(R.layout.fragment_category_post, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            category = (MyCategory) bundle.getSerializable("category");
        }

        // Tool Bar
        // Title
        mCategoryTitle = v.findViewById(R.id.categoryTitle);
        mCategoryTitle.setText(category.getTitle());

        posts = new ArrayList<>();

        new CategoryPostFragment.PostAsyncTask(db.postDAO()).execute(category.getCategoryID());

        // Adapter 초기화
        mRecyclerAdapter = new CategoryPostRecyclerAdapter();
        mRecyclerAdapter.setCategory(category);

        // 임시 객체로 RecyclerView 설정
        MyPost initPost = new MyPost(0, "", "", "");
        ArrayList<MyPost> intiData = new ArrayList<>();
        intiData.add(initPost);
        mRecyclerAdapter.setMyPostArrayList(intiData);

        // RecyclerView 초기화
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
    if(category.getType()!=100){
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));    // Grid Layout 사용
    } else {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));             // Linear Layout 사용
    }
        mRecyclerView.setAdapter(mRecyclerAdapter);

        return v;
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public class PostAsyncTask extends AsyncTask<Integer, Void, Void> {
        private PostDAO postDAO;

        public  PostAsyncTask(PostDAO postDAO){
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            posts.addAll(postDAO.findByID(id[0]));
            mRecyclerAdapter.setMyPostArrayList(posts);
            return null;
        }
    }
}