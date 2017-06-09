package com.example.mayankchauhan.inote.onscreen.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.example.mayankchauhan.inote.utils.OnCheckListItemClicked;
import com.example.mayankchauhan.inote.utils.OnRecyclerViewItemCLickListener;
import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.checklist.model.CheckListBeans;
import com.example.mayankchauhan.inote.checklist.database.CheckListDatabaseHandler;
import com.example.mayankchauhan.inote.notes.adapter.CombinedRecyclerAdapter;
import com.example.mayankchauhan.inote.notes.model.NotesBeans;
import com.example.mayankchauhan.inote.notes.database.NotesDatabaseHandler;
import com.example.mayankchauhan.inote.onscreen.ListNoteDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class OnScreenActivity extends AppCompatActivity implements View.OnClickListener,
        OnRecyclerViewItemCLickListener,OnCheckListItemClicked{

    private RecyclerView listNoteRecyclerView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<Object> list = new ArrayList<>();
    private CombinedRecyclerAdapter adapter;

    public static final String BUNDLE_NOTE = "note_bundle";
    public static final String BUNDLE_LIST = "list_bundle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_screen);

        listNoteRecyclerView = (RecyclerView) findViewById(R.id.list_note_recycler_view);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(OnScreenActivity.this);

        getData();

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        listNoteRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new CombinedRecyclerAdapter(list,this);
        adapter.setOnRecyclerViewItemCLickListener(this);
        adapter.setOnCheckListItemClicked(this);

        listNoteRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        ListNoteDialogFragment dialogFragment = new ListNoteDialogFragment();
        dialogFragment.show(getFragmentManager(),"Choice Dialog");

    }
    private void getData()
    {
        list.clear();

        CheckListDatabaseHandler databaseHandler = new CheckListDatabaseHandler(OnScreenActivity.this);
        List<CheckListBeans> beansList = databaseHandler.getAllCheckList();

        for (CheckListBeans beans:beansList)
        {
            list.add(beans);
        }
        NotesDatabaseHandler db = new NotesDatabaseHandler(OnScreenActivity.this);
        List<NotesBeans> beanses = db.getAllNotes();
        for (NotesBeans b:beanses)
        {
            list.add(b);
        }

    }

    @Override
    public void onItemClicked(Bundle bundle) {
        Intent intent = new Intent(this,OnClickNoteActivity.class);
        intent.putExtra(BUNDLE_NOTE,bundle);
        startActivity(intent);
    }

    @Override
    public void onCheckListItemClicked(Bundle bundle) {
        Intent intent = new Intent(this,OnClickCheckListActivity.class);
        intent.putExtra(BUNDLE_LIST,bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to exit iNote?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
