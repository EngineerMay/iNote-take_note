package com.example.mayankchauhan.inote.notes.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankchauhan.inote.utils.OnCheckListItemClicked;
import com.example.mayankchauhan.inote.utils.OnRecyclerViewItemCLickListener;
import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.checklist.database.CheckListDatabaseConstants;
import com.example.mayankchauhan.inote.checklist.model.CheckListBeans;
import com.example.mayankchauhan.inote.notes.database.NotesDatabaseConstants;
import com.example.mayankchauhan.inote.notes.model.NotesBeans;

import java.util.List;

/**
 * Created by mayankchauhan on 07/06/17.
 */

public class CombinedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> list;
    private final int NOTE = 1;
    private final int CHECKLIST = 2;
    private Context context;
    private OnRecyclerViewItemCLickListener onRecyclerViewItemCLickListener;
    private OnCheckListItemClicked onCheckListItemClicked;

    public CombinedRecyclerAdapter(List<Object> list,Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnCheckListItemClicked(OnCheckListItemClicked onCheckListItemClicked) {
        this.onCheckListItemClicked = onCheckListItemClicked;
    }

    public void setOnRecyclerViewItemCLickListener(OnRecyclerViewItemCLickListener onRecyclerViewItemCLickListener) {
        this.onRecyclerViewItemCLickListener = onRecyclerViewItemCLickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof NotesBeans)
        {
            return NOTE;
        }
        if (list.get(position) instanceof CheckListBeans)
        {
            return CHECKLIST;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType)
        {
            case NOTE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onscreen_row_layout,parent,false);
                return new NotesViewHolder(view);
            case CHECKLIST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onscreen_checklist_row_layout,parent,false);
                return new OnScrCheckListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {

        switch (holder.getItemViewType())
        {
            case NOTE:
                NotesViewHolder v1 = (NotesViewHolder) holder;
                configureNotesViewHolder(v1,position);
                break;
            case CHECKLIST:
                OnScrCheckListViewHolder v2 = (OnScrCheckListViewHolder) holder;
                configureCheckListViewHolder(v2,position);
                break;
        }
    }
    private void configureNotesViewHolder(final NotesViewHolder viewHolder,final int pos)
    {
        final NotesBeans beans = (NotesBeans) list.get(pos);
        if (null != beans) {
            viewHolder.title.setText(beans.getTitle());
            viewHolder.note.setText(beans.getNote());

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();

                    bundle.putInt(NotesDatabaseConstants.ID,beans.getiD());
                    bundle.putString(NotesDatabaseConstants.TITLE,beans.getTitle());
                    bundle.putString(NotesDatabaseConstants.NOTE,beans.getNote());
                    bundle.putString(NotesDatabaseConstants.IMGPATH,beans.getImgPath());
                    bundle.putString(NotesDatabaseConstants.NOTES_DATE,beans.getmDate());
                    bundle.putString(NotesDatabaseConstants.NOTES_TIME,beans.getmTime());

                    onRecyclerViewItemCLickListener.onItemClicked(bundle);
                }
            });
        }

    }
    private void configureCheckListViewHolder(final OnScrCheckListViewHolder checkListViewHolder,final int position)
    {
        final CheckListBeans beans = (CheckListBeans) list.get(position);
        if (null != beans) {
            checkListViewHolder.onScreenListName.setText(beans.getCheckListName());

            checkListViewHolder.checkListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    bundle.putInt(CheckListDatabaseConstants.CHECKLIST_ID,beans.getCheckListId());
                    bundle.putString(CheckListDatabaseConstants.CHECKLIST_NAME,beans.getCheckListName());
                    bundle.putString(CheckListDatabaseConstants.CHECKLIST_TASK,beans.getCheckListTasks());
                    bundle.putString(CheckListDatabaseConstants.CHECKLIST_DATE,beans.getCheckListDate());
                    bundle.putString(CheckListDatabaseConstants.CHECKLIST_TIME,beans.getCheckListTime());

                    onCheckListItemClicked.onCheckListItemClicked(bundle);
                }
            });
        }
    }
}
