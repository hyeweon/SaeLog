package com.we.saelog;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;
import com.we.saelog.room.MyPost;
import com.we.saelog.room.PostDAO;
import com.we.saelog.room.PostDB;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPostFragment extends Fragment {

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

    PostDB postDB;
    CategoryDB categoryDB;

    private Toolbar mToolbar;
    private Spinner mSpinner;
    private Button mBtnBack;
    private EditText mTitle;
    private ViewGroup mContainer;
    private View view;
    ArrayList<EditText> shortTexts;
    ArrayList<EditText> longTexts;
    ArrayList<CheckBox> checkBoxes;
    ArrayList<RatingBar> ratingBars;
    ArrayList<ImageView> imageViews;

    String text;
    ArrayList<MyCategory> categories;
    ArrayList<String> categoryTitles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // DB 호출
        postDB = PostDB.getAppDatabase(getActivity());
        categoryDB = CategoryDB.getAppDatabase(getActivity());

        // Tool Bar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View에 inflate
        View v = inflater.inflate(R.layout.fragment_new_post, container, false);

        // Tool Bar
        mToolbar = v.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        // 취소 버튼
        mBtnBack = v.findViewById(R.id.btnBack);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, ((MainActivity) getActivity()).preFragment).commit();
            }
        });

        categories = new ArrayList<>();
        categoryTitles = new ArrayList<>();

        // Spinner
        mSpinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.item_category_dropdown, categoryTitles);
        mSpinner.setAdapter(categoryAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                makeContentInput(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                makeContentInput(0);
            }
        });

        // LiveData observe
        categoryDB.categoryDAO().getAll().observe(getActivity(), new Observer<List<MyCategory>>() {
            @Override
            public void onChanged(List<MyCategory> myCategoryList) {
                for (int i=0; i<myCategoryList.size(); i++){
                    categories.add(myCategoryList.get(i));
                    categoryTitles.add(myCategoryList.get(i).getTitle());
                }
                categoryAdapter.notifyDataSetChanged();
            }
        });

        mTitle = v.findViewById(R.id.title);
        mContainer = v.findViewById(R.id.container);

        shortTexts = new ArrayList<>();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.new_post_toolbar_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnSave:
                text = shortTexts.get(0).getText().toString();
                if(mTitle.getText().toString().trim().length() <= 0) {      // 제목이 입력되지 않은 경우
                    Toast.makeText(getActivity(), "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    // DB에 새로운 카테고리 추가를 위한 AsyncTask 호출
                    new InsertAsyncTask(postDB.postDAO()).execute(new MyPost(0, mTitle.getText().toString()));
                    mTitle.setText(null);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 레이아웃 구성
    public void makeContentInput(int index){
        mContainer.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i=1; categories.get(index).getContentType(i)!=0; i++){
            switch (categories.get(index).getContentType(i)){
                case 1:
                    view = inflater.inflate(R.layout.content_text_short, mContainer, true);
                    shortTexts.add(view.findViewById(R.id.text_short));
                    break;
                case 2:
                    view = inflater.inflate(R.layout.content_ratingbar, mContainer, true);
                    break;
                case 3:
                    view = inflater.inflate(R.layout.content_ratingbar, mContainer, true);
                    break;
                case 4:
                    view = inflater.inflate(R.layout.content_ratingbar, mContainer, true);
                    break;
                case 5:
                    view = inflater.inflate(R.layout.content_ratingbar, mContainer, true);
                    break;
                default:
                    view = inflater.inflate(R.layout.content_ratingbar, mContainer, true);
            }
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