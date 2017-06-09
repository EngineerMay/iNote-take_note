package com.example.mayankchauhan.inote.checklist.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mayankchauhan.inote.checklist.broadcast.Alarm;
import com.example.mayankchauhan.inote.checklist.database.CheckListDatabaseConstants;
import com.example.mayankchauhan.inote.utils.OnDeleteButtonClicked;
import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.checklist.model.CheckListBeans;
import com.example.mayankchauhan.inote.checklist.adapter.CheckListRecyclerAdapter;
import com.example.mayankchauhan.inote.checklist.database.CheckListDatabaseHandler;
import com.example.mayankchauhan.inote.onscreen.activity.OnScreenActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class CheckListActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,OnDeleteButtonClicked {

    private ArrayList<String> list = new ArrayList<>();
    private String finalCheckList = "";
    private RecyclerView checkListRecycler;
    private CheckListRecyclerAdapter adapter;
    private Button checkListReminder;
    private int dYear,dMonth,dDay,tHour,tMin;
    private Calendar calendar;
    private  String reminderDate = "date";
    private String reminderTime = "time";
    private EditText checkListName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        checkListRecycler = (RecyclerView) findViewById(R.id.checklist_recycler);
        checkListRecycler.setLayoutManager(new LinearLayoutManager(this));


        checkListName = (EditText) findViewById(R.id.checklist_name_editText);

        checkListReminder = (Button) findViewById(R.id.checklistButton);

        checkListReminder.setOnClickListener(this);

        calendar = Calendar.getInstance();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.add_button :

                final EditText input = new EditText(CheckListActivity.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(CheckListActivity.this);
                builder.setTitle("Add a todo task");
                builder.setView(input);
                builder.setPositiveButton("Add task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String inputText = input.getText().toString().trim();
                        finalCheckList = finalCheckList+inputText+",";
                        list.add(inputText);
                        updateRecycler();
                    }
                });
                builder.show();
                break;
            case R.id.saveCheckList:
                String str = checkListName.getText().toString().trim();
                finalCheckList = finalCheckList.substring(0,finalCheckList.length()-1);
                CheckListBeans checkListBeans = new CheckListBeans(str,finalCheckList,reminderDate,reminderTime);

                CheckListDatabaseHandler db = new CheckListDatabaseHandler(CheckListActivity.this);
                int ID = db.addCheckList(checkListBeans);
                setCheckListAlarm(ID);

                Intent intent = new Intent(this, OnScreenActivity.class);
                finish();
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setCheckListAlarm(int id)
    {
        Calendar currentCalendar = Calendar.getInstance();
        int timeDiff = (int) ((calendar.getTimeInMillis()) - (currentCalendar.getTimeInMillis()));

        Intent intent = new Intent(this, Alarm.class);
        intent.putExtra(CheckListDatabaseConstants.CHECKLIST_REMINDER_ID,Integer.toString(id));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CheckListActivity.this.getApplicationContext(),id,intent,0);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        manager.set(manager.RTC_WAKEUP, System.currentTimeMillis()+ timeDiff,pendingIntent);
    }
    private void updateRecycler()
    {
        adapter = new CheckListRecyclerAdapter(CheckListActivity.this,list);
        adapter.setOnDeleteButtonClicked(this);
        adapter.notifyItemInserted(list.size());
        checkListRecycler.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        showDatePicker();
    }
    private void showDatePicker()
    {
        dYear = calendar.get(Calendar.YEAR);
        dMonth = calendar.get(Calendar.MONTH)+1;
        dDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,dYear,dMonth,dDay);
        datePickerDialog.show();

    }
    private void showTimePicker()
    {
        tHour = calendar.get(Calendar.HOUR_OF_DAY);
        tMin = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,this,tHour,tMin,false);
        timePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        dYear = year;
        dMonth = month+1;
        dDay = dayOfMonth;

        calendar.set(Calendar.MONTH,(dMonth-1));
        calendar.set(Calendar.YEAR,dYear);
        calendar.set(Calendar.DAY_OF_MONTH,dDay);

        reminderDate =dayOfMonth+"/"+dMonth+"/"+year;
        showTimePicker();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        tHour = hourOfDay;
        tMin = minute;

        calendar.set(Calendar.HOUR_OF_DAY,tHour);
        calendar.set(Calendar.MINUTE,tMin);
        calendar.set(Calendar.SECOND,0);
        if (minute < 10) {
            reminderTime = hourOfDay + ":" + "0" + minute;
        } else {
            reminderTime = hourOfDay + ":" + minute;
        }
    }

    @Override
    public void onDeleteClicked(int position) {
        adapter = new CheckListRecyclerAdapter(CheckListActivity.this,list);
        adapter.setOnDeleteButtonClicked(this);
        list.remove(position);
        adapter.notifyItemRemoved(position);
        checkListRecycler.setAdapter(adapter);
    }
}