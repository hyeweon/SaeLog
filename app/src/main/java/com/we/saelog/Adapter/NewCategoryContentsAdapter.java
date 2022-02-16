package com.we.saelog.Adapter;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;

import java.util.ArrayList;

public class NewCategoryContentsAdapter extends RecyclerView.Adapter<NewCategoryContentsAdapter.ViewHolder> {
    private ArrayList<Integer> contentsArrayList;
    String[] contentTitles = new String[8];

    @NonNull
    @Override
    public NewCategoryContentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_category_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewCategoryContentsAdapter.ViewHolder holder, int position) {
        holder.onBind(contentsArrayList.get(position));
    }

    public void setContentsArrayList(ArrayList<Integer> contentsArrayList) {
        this.contentsArrayList = contentsArrayList;
        notifyItemInserted(contentsArrayList.size() - 1);
    }

    public void deleteItem(int position){
        contentsArrayList.remove(contentsArrayList.size() - 1);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return contentsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mContentNum;
        EditText mcontentTitle;
        Spinner mSpinner;
        ArrayAdapter contentTypeAdapter;
        ImageButton mBtnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 항목 커스텀
            mContentNum = (TextView) itemView.findViewById(R.id.contentNum);
            mcontentTitle = (EditText) itemView.findViewById(R.id.contentTitle);
            mcontentTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mcontentTitle.setTypeface(Typeface.DEFAULT_BOLD);
                    contentTitles[getAdapterPosition()] = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mcontentTitle.setTypeface(Typeface.DEFAULT_BOLD);
                }
            });
            mSpinner = (Spinner) itemView.findViewById(R.id.spinner);
            contentTypeAdapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.content_types, R.layout.item_content_type);
            mSpinner.setAdapter(contentTypeAdapter);
            mBtnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    deleteItem(position);
                }
            });
        }

        public void onBind(Integer integer) {
            mContentNum.setText(integer.toString() + ". ");
        }
    }
}
