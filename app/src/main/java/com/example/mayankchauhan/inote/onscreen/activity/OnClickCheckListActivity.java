package com.example.mayankchauhan.inote.onscreen.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mayankchauhan.inote.checklist.broadcast.Alarm;
import com.example.mayankchauhan.inote.utils.OnDeleteButtonClicked;
import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.checklist.adapter.CheckListRecyclerAdapter;
import com.example.mayankchauhan.inote.checklist.database.CheckListDatabaseConstants;
import com.example.mayankchauhan.inote.checklist.database.CheckListDatabaseHandler;

import java.util.ArrayList;

public class OnClickCheckListActivity extends AppCompatActivity implements OnDeleteButtonClicked {

    private TextView listnameTextview;
    private RecyclerView listRecyclerview;
    private TextView dateTextView;
    private TextView timeTextView;
    private ArrayList<String> list = new ArrayList<>();
    private CheckListRecyclerAdapter adapter;
    private int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_check_list);

        listnameTextview = (TextView) findViewById(R.id.listname_textview);
        listRecyclerview = (RecyclerView) findViewById(R.id.list_recyclerview);

        dateTextView = (TextView) findViewById(R.id.textview_date);
        timeTextView = (TextView) findViewById(R.id.textview_time);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(OnScreenActivity.BUNDLE_LIST);


        int ID = bundle.getInt(CheckListDatabaseConstants.CHECKLIST_ID);
        id=ID;
        String name = bundle.getString(CheckListDatabaseConstants.CHECKLIST_NAME);
        String todoList = bundle.getString(CheckListDatabaseConstants.CHECKLIST_TASK);
        String todoTime = bundle.getString(CheckListDatabaseConstants.CHECKLIST_TIME);
        String todoDate = bundle.getString(CheckListDatabaseConstants.CHECKLIST_DATE);

        adapter = new CheckListRecyclerAdapter(this,list);
        adapter.setOnDeleteButtonClicked(this);
        listnameTextview.setText(name);
        if (todoDate.equals("date") && todoTime.equals("time")) {
            dateTextView.setText("");
            timeTextView.setText("");
        }
        else {
            dateTextView.setText(todoDate);
            timeTextView.setText(todoTime);
        }

        makeArrayList(todoList);

        listRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerview.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        CheckListDatabaseHandler db = new CheckListDatabaseHandler(this);
        db.deleteCheckList(id);
        cancelCheckListAlarm(id);

        finish();
        startActivity(new Intent(this,OnScreenActivity.class));
        return super.onOptionsItemSelected(item);
    }
    private void makeArrayList(String todolist)
    {
        list.clear();
        String[] array = todolist.split(",");
        for (String str: array)
        {
            list.add(str);
        }

    }
    private void cancelCheckListAlarm(int id)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, Alarm.class);
        intent.putExtra(CheckListDatabaseConstants.CHECKLIST_REMINDER_ID,Integer.toString(id));
        PendingIntent pi = PendingIntent.getBroadcast(this,id,intent,0);
        alarmManager.cancel(pi);
    }
    @Override
    public void onDeleteClicked(int position) {
        adapter = new CheckListRecyclerAdapter(this,list);
        adapter.setOnDeleteButtonClicked(this);
        list.remove(position);
        adapter.notifyItemRemoved(position);
        listRecyclerview.setAdapter(adapter);
    }
}
