package com.example.mayankchauhan.inote.checklist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mayankchauhan.inote.R;

import org.w3c.dom.Text;

/**
 * Created by mayankchauhan on 06/06/17.
 */

public class CheckListViewHolder extends RecyclerView.ViewHolder {

    TextView toDoCheckList;
    Button deleteTaskButton;
    public CheckListViewHolder(View itemView) {
        super(itemView);
        toDoCheckList = (TextView) itemView.findViewById(R.id.todo_checklist);
        deleteTaskButton = (Button) itemView.findViewById(R.id.delete_task_button);
    }
}
