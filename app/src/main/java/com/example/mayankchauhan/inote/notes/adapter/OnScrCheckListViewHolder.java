package com.example.mayankchauhan.inote.notes.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mayankchauhan.inote.R;

/**
 * Created by mayankchauhan on 07/06/17.
 */

public class OnScrCheckListViewHolder extends RecyclerView.ViewHolder {

    TextView onScreenListName;
    View checkListView;
    public OnScrCheckListViewHolder(View itemView) {
        super(itemView);

        checkListView = itemView;
        onScreenListName = (TextView) itemView.findViewById(R.id.onscreen_checklist_name);
    }
}
