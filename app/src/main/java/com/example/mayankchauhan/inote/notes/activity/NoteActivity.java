package com.example.mayankchauhan.inote.notes.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.notes.model.NotesBeans;
import com.example.mayankchauhan.inote.notes.database.NotesDatabaseConstants;
import com.example.mayankchauhan.inote.notes.database.NotesDatabaseHandler;
import com.example.mayankchauhan.inote.notes.broadcastReceiver.AlarmReceiver;
import com.example.mayankchauhan.inote.onscreen.activity.OnScreenActivity;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener
        ,TimePickerDialog.OnTimeSetListener {

    private EditText titleEditText;
    private EditText noteEditText;
    private Button saveNoteButton;
    private Button addImageButton;
    private Button setReminderButton;
    private int dYear,dMonth,dDay,tHour,tMin;

    public static final int RESULT_LOAD_IMAGE = 100;

    public static String selectedImagePath="";
    public static String reminderDate = "date";
    public static String reminderTime = "time";
    private int finalTime = 0;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleEditText = (EditText) findViewById(R.id.title_editText);
        noteEditText = (EditText) findViewById(R.id.note_editText);
        saveNoteButton = (Button) findViewById(R.id.saveNote_button);
        saveNoteButton.setOnClickListener(NoteActivity.this);

        addImageButton = (Button) findViewById(R.id.addImage_button);
        addImageButton.setOnClickListener(this);

        setReminderButton = (Button) findViewById(R.id.setReminder_button);
        setReminderButton.setOnClickListener(this);

        calendar = Calendar.getInstance();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.saveNote_button :
                String title = titleEditText.getText().toString().trim();
                String note = noteEditText.getText().toString().trim();

                final NotesDatabaseHandler db = new NotesDatabaseHandler(NoteActivity.this);
                int mID = db.addNote(new NotesBeans(title, note,selectedImagePath,reminderDate,reminderTime));
                setALarm(mID);

                titleEditText.setText("");
                noteEditText.setText("");

                finish();
                Intent intent = new Intent(NoteActivity.this, OnScreenActivity.class);
                startActivity(intent);
                break;
            case R.id.addImage_button:
                Intent intent1 = new Intent(NoteActivity.this,ImagePickerActivity.class);
                startActivityForResult(intent1,RESULT_LOAD_IMAGE);
                break;
            case R.id.setReminder_button:
                showDatePicker();
                break;
        }
    }
    private void showDatePicker()
    {
        dYear = calendar.get(Calendar.YEAR);
        dMonth = calendar.get(Calendar.MONTH);
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

        calendar.set(Calendar.YEAR,dYear);
        calendar.set(Calendar.MONTH,(dMonth-1));
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

        if (minute <10) {
            reminderTime = hourOfDay + ":" +"0"+minute;
        }
        else {
            reminderTime = hourOfDay + ":" + minute;
        }
    }
    private void setALarm(int id)
    {
        Calendar currentCalendar = Calendar.getInstance();
        int timeDiff = (int) ((calendar.getTimeInMillis()) - (currentCalendar.getTimeInMillis()));

        if ("date" != reminderDate && "time" != reminderTime) {
            Intent intent = new Intent(NoteActivity.this, AlarmReceiver.class);
            intent.putExtra(NotesDatabaseConstants.REMINDER_ID, Integer.toString(id));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteActivity.this.getApplicationContext(), id, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeDiff , pendingIntent);
        }
    }
}
