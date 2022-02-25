package com.we.saelog.Adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;
import com.we.saelog.room.MyCategory;

import java.util.ArrayList;

public class NewPostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyCategory category;
    private ArrayList<String> contents;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE);

        switch (viewType){
            case 1:
                v = inflater.inflate(R.layout.content_text_short, parent, false);
                return new textViewHolder(v);
            case 2:
                v = inflater.inflate(R.layout.content_text_long, parent, false);
                return new textViewHolder(v);
            case 3:
                v = inflater.inflate(R.layout.content_checkbox, parent, false);
                return new checkboxViewholder(v);
            case 4:
                v = inflater.inflate(R.layout.content_ratingbar, parent, false);
                return new ratingbarViewholder(v);
            case 5:
                v = inflater.inflate(R.layout.content_text_short, parent, false);
                return new textViewHolder(v);
            default:
                v = inflater.inflate(R.layout.content_text_short, parent, false);
                return new textViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof textViewHolder)
        {
            ((textViewHolder)holder).onBind(category.getContentTitle(position + 1));
        }
        else if(holder instanceof checkboxViewholder)
        {
            ((checkboxViewholder)holder).onBind(category.getContentTitle(position + 1));
        }
        else if(holder instanceof ratingbarViewholder)
        {
            ((ratingbarViewholder)holder).onBind(category.getContentTitle(position + 1));
        }
    }

    public void setCategory(MyCategory category) {
        this.category = category;
        notifyDataSetChanged();

        contents = new ArrayList<>();
        for (int i=0; i<category.getContentsNum(); i++){
            contents.add("");
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

    public ArrayList<String> getContents() {
        return contents;
    }

    public class textViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public EditText mText;

        public textViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.contentTitle);
            mText = itemView.findViewById(R.id.text);
            mText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    contents.set(getAdapterPosition(),charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

        public void onBind(String item) {
            mTitle.setText(item);
        }
    }

    public class checkboxViewholder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mCurrState;
        public CheckBox mCheckbox;

        public checkboxViewholder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.contentTitle);
            mCurrState = itemView.findViewById(R.id.currState);
            mCheckbox = itemView.findViewById(R.id.checkbox);
            mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b==true){
                        contents.set(getAdapterPosition(), "true");
                        mCurrState.setText("checked");
                    }
                    else {
                        contents.set(getAdapterPosition(), "false");
                        mCurrState.setText("unchecked");
                    }
                }
            });
        }

        public void onBind(String item) {
            mTitle.setText(item);
        }
    }

    public class ratingbarViewholder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public RatingBar mRatingbar;

        public ratingbarViewholder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.contentTitle);
            mRatingbar = itemView.findViewById(R.id.ratingbar);
            mRatingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    contents.set(getAdapterPosition(), String.valueOf(v));
                }
            });
        }

        public void onBind(String item) {
            contents.set(getAdapterPosition(), "0");

            mTitle.setText(item);
        }
    }
}
