package com.we.saelog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.Adapter.MyPageRecyclerAdapter;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryDB db;
    public SharedPreferences prefs;
    public MyPageRecyclerAdapter mRecyclerAdapter;

    public MyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MypageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // DB 호출
        db = CategoryDB.getAppDatabase(getActivity());

        prefs = getActivity().getSharedPreferences("Pref", getActivity().MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View에 inflate
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);

        ImageButton btnNewCategory = (ImageButton) v.findViewById(R.id.btnNewCategory);
        btnNewCategory.setOnClickListener(this);
        ImageButton btnSetting = (ImageButton) v.findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(this);

        // 이름
        String name = prefs.getString("name", "");
        TextView mName = (TextView) v.findViewById(R.id.name);
        mName.setText(name);

        // 자기소개
        String bio = prefs.getString("bio", "");
        TextView mBio = (TextView) v.findViewById(R.id.bio);
        mBio.setText(bio);

        // 프로필 이미지
        ImageView profileImage = (ImageView) v.findViewById(R.id.profileImage);
        String strProfileImage = prefs.getString("profileImage", "");
        Bitmap bitmapProfileImage = StringToBitmap(strProfileImage);
        profileImage.setImageBitmap(bitmapProfileImage);

        // Adapter 초기화
        mRecyclerAdapter = new MyPageRecyclerAdapter();

        // 임시 객체로 RecyclerView 설정
        MyCategory initCategory = new MyCategory("", "", "#ffffff", 0, 0);
        List<MyCategory> intiData = new ArrayList<>();
        intiData.add(initCategory);
        mRecyclerAdapter.setMyCategoryArrayList(intiData);

        // RecyclerView 초기화
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));     // Grid Layout 사용
        mRecyclerView.setAdapter(mRecyclerAdapter);

        // LiveData observe
        db.categoryDAO().getAll().observe(getActivity(), new Observer<List<MyCategory>>() {
            @Override
            public void onChanged(List<MyCategory> myCategoryList) {
                mRecyclerAdapter.setMyCategoryArrayList(myCategoryList);   // 티켓 목록에 반영
            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            // + 버튼을 눌렀을 때 새로운 카테고리를 생성하기 위한 Activity 실행
            case R.id.btnNewCategory:
                intent = new Intent(getActivity(), NewCategoryActivity.class);
                startActivity(intent);
                break;
            // 설정 아이콘을 눌렀을 때 새로운 카테고리를 생성하기 위한 Activity 실행
            case R.id.btnSetting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    // from https://stickode.com/detail.html?no=1297
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);// String 화 된 이미지를  base64방식으로 인코딩하여 byte배열을 만듬
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);//byte배열을 bitmapfactory 메소드를 이용하여 비트맵으로 바꿔준다.
            return bitmap;//만들어진 bitmap을 return
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}