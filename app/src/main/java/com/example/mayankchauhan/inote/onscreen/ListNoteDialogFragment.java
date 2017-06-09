package com.example.mayankchauhan.inote.onscreen;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.mayankchauhan.inote.checklist.activity.CheckListActivity;
import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.notes.activity.NoteActivity;

/**
 * Created by mayankchauhan on 30/05/17.
 */

public class ListNoteDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public ListNoteDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.Choose).setItems(R.array.listNote,this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        Intent intent;
        switch (which)
        {
            case 0:
                intent = new Intent(getActivity(),NoteActivity.class);
                startActivity(intent);
                dialog.dismiss();
                break;
            case 1:
                intent = new Intent(getActivity(),CheckListActivity.class);
                startActivity(intent);
                dialog.dismiss();
                break;
            default:
                break;
        }

    }
}
