package com.we.saelog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.Adapter.TimelineRecyclerAdapter;
import com.we.saelog.room.MyPost;
import com.we.saelog.room.PostDB;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostSearchFragment newInstance(String param1, String param2) {
        PostSearchFragment fragment = new PostSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    PostDB db;
    TimelineRecyclerAdapter mRecyclerAdapter;

    private Toolbar mToolbar;
    private EditText mSearchTitle;

    List<MyPost> posts;
    List<MyPost> filteredPosts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // DB 호출
        db = PostDB.getAppDatabase(getActivity());

        // Tool Bar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        // view에 inflate
        View v = inflater.inflate(R.layout.fragment_post_search, container, false);

        // Tool Bar
        mToolbar = v.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_arrowback);

        mSearchTitle = v.findViewById(R.id.searchTitle);
        mSearchTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredPosts.clear();
                if(s.length() != 0){
                    for (int i = 0; i < posts.size(); i++) {
                        if (posts.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                            filteredPosts.add(posts.get(i));
                        }
                    }
                }

                mRecyclerAdapter.setMyPostArrayList(filteredPosts);
            }
        });

        // Adapter 초기화
        mRecyclerAdapter = new TimelineRecyclerAdapter();
        posts = new ArrayList<>();
        filteredPosts = new ArrayList<>();
        mRecyclerAdapter.setMyPostArrayList(posts);

        // RecyclerView 초기화
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));     // Linear Layout 사용
        mRecyclerView.setAdapter(mRecyclerAdapter);

        // LiveData observe
        db.postDAO().getAll().observe(getActivity(), new Observer<List<MyPost>>() {
            @Override
            public void onChanged(List<MyPost> myTicketsList) {
                posts = myTicketsList;
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.timeline_toolbar_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new TimelineFragment()).commit();
                break;
            case R.id.btnSearch:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new PostSearchFragment()).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}