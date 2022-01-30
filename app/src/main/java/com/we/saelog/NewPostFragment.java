package com.we.saelog;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.we.saelog.room.MyPost;
import com.we.saelog.room.PostDAO;
import com.we.saelog.room.PostDB;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPostFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPostFragment newInstance(String param1, String param2) {
        NewPostFragment fragment = new NewPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    androidx.appcompat.widget.Toolbar toolbar;
    private EditText mTitle;

    PostDB db;

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
        // View에 inflate
        View v = inflater.inflate(R.layout.fragment_new_post, container, false);

        mTitle = v.findViewById(R.id.title);
        Button btnSave = (Button) v.findViewById(R.id.btnSave1);
        btnSave.setOnClickListener(this);
        toolbar = v.findViewById(R.id.toolbar);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.new_post_toolbar_items,menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnSave:
                        Toast.makeText(getActivity(),"등록 버튼 클릭",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave1:
                if(mTitle.getText().toString().trim().length() <= 0) {        // 제목이 입력되지 않은 경우
                    Toast.makeText(getActivity(), "공연명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    // DB에 새로운 포스트 추가를 위한 AsyncTask 호출
                    new InsertAsyncTask(db.postDAO())
                            .execute(new MyPost(0, mTitle.getText().toString()));
                    mTitle.setText(null);
                }
                break;
        }
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class InsertAsyncTask extends AsyncTask<MyPost, Void, Void> {
        private PostDAO postDAO;

        public  InsertAsyncTask(PostDAO ticketDAO){
            this.postDAO = ticketDAO;
        }

        @Override
        protected Void doInBackground(MyPost... myPost) {
            postDAO.insert(myPost[0]); // DB에 새로운 포스트 추가
            return null;
        }
    }
}