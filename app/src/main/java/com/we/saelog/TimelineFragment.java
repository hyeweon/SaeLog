package com.we.saelog;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.we.saelog.Adapter.TimelineRecyclerAdapter;
import com.we.saelog.room.MyPost;
import com.we.saelog.room.PostDAO;
import com.we.saelog.room.PostDB;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance(String param1, String param2) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    PostDB db;
    TimelineRecyclerAdapter mRecyclerAdapter;
    TimelineRecyclerAdapter mHeartedRecyclerAdapter;

    TabLayout tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // DB 호출
        db = PostDB.getAppDatabase(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view에 inflate
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);

        // Adapter 초기화
        mRecyclerAdapter = new TimelineRecyclerAdapter();
        mHeartedRecyclerAdapter = new TimelineRecyclerAdapter();

        // 임시 객체로 RecyclerView 설정
        MyPost initPost = new MyPost(0, " ");
        List<MyPost> intiData = new ArrayList<>();
        intiData.add(initPost);
        mRecyclerAdapter.setMyPostArrayList(intiData);
        mHeartedRecyclerAdapter.setMyPostArrayList(intiData);

        // RecyclerView 초기화
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));     // Linear Layout 사용
        mRecyclerView.setAdapter(mRecyclerAdapter);

        // RecyclerView 초기화
        RecyclerView mHeartedRecyclerView = (RecyclerView) v.findViewById(R.id.heartedRecyclerView);
        mHeartedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  // Linear Layout 사용
        mHeartedRecyclerView.setAdapter(mHeartedRecyclerAdapter);

        // LiveData observe
        db.postDAO().getAll().observe(getActivity(), new Observer<List<MyPost>>() {
            @Override
            public void onChanged(List<MyPost> myTicketsList) {
                mRecyclerAdapter.setMyPostArrayList(myTicketsList);   // 티켓 목록에 반영
            }
        });

        db.postDAO().getHearted(true).observe(getActivity(), new Observer<List<MyPost>>() {
            @Override
            public void onChanged(List<MyPost> myTicketsList) {
                mHeartedRecyclerAdapter.setMyPostArrayList(myTicketsList);   // 티켓 목록에 반영
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabs = view.findViewById(R.id.tabs);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView mHeartedRecyclerView = (RecyclerView) view.findViewById(R.id.heartedRecyclerView);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if (position == 0){                 // 첫 번째 탭 선택 시
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mHeartedRecyclerView.setVisibility(View.GONE);
                }else if (position == 1){           // 두 번째 탭 선택 시
                    mHeartedRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
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