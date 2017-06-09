package com.example.mayankchauhan.inote.notes.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mayankchauhan.inote.R;

/**
 * Created by mayankchauhan on 02/06/17.
 */

public class NotesViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView note;
    View mView;
    public NotesViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        title = (TextView) itemView.findViewById(R.id.title_textView);
        note = (TextView) itemView.findViewById(R.id.note_textView);
    }
}
