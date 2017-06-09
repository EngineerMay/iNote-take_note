package com.example.mayankchauhan.inote.checklist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankchauhan.inote.utils.OnDeleteButtonClicked;
import com.example.mayankchauhan.inote.R;

import java.util.List;

/**
 * Created by mayankchauhan on 06/06/17.
 */

public class CheckListRecyclerAdapter extends RecyclerView.Adapter<CheckListViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<String> list;
    private OnDeleteButtonClicked onDeleteButtonClicked;

    public CheckListRecyclerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setOnDeleteButtonClicked(OnDeleteButtonClicked onDeleteButtonClicked) {
        this.onDeleteButtonClicked = onDeleteButtonClicked;
    }

    @Override
    public CheckListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.onclick_checklist,parent,false);
        return new CheckListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckListViewHolder holder,final int position) {

        String lsString = list.get(position);
        holder.toDoCheckList.setText(lsString);
        holder.deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClicked.onDeleteClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
