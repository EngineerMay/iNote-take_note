package com.example.mayankchauhan.inote.onscreen.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.notes.broadcastReceiver.AlarmReceiver;
import com.example.mayankchauhan.inote.notes.database.NotesDatabaseConstants;
import com.example.mayankchauhan.inote.notes.database.NotesDatabaseHandler;
import com.squareup.picasso.Picasso;

import java.io.File;

import static com.example.mayankchauhan.inote.R.id.image_click;

public class OnClickNoteActivity extends AppCompatActivity {

    private TextView title;
    private TextView note;
    private ImageView image;
    private TextView date;
    private TextView time;
    private int id=-1;
    private AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_note);

        title = (TextView) findViewById(R.id.title_click);
        note = (TextView) findViewById(R.id.note_click);
        image = (ImageView) findViewById(image_click);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(OnScreenActivity.BUNDLE_NOTE);

        title.setText(bundle.getString(NotesDatabaseConstants.TITLE));
        note.setText(bundle.getString(NotesDatabaseConstants.NOTE));
        String dateT = bundle.getString(NotesDatabaseConstants.NOTES_DATE);
        String timeT = bundle.getString(NotesDatabaseConstants.NOTES_TIME);
        id = bundle.getInt(NotesDatabaseConstants.ID);

        if (dateT.equals("date") && timeT.equals("time"))
        {
            date.setText("");
            time.setText("");
        }
        else {
            date.setText(dateT);
            time.setText(timeT);
        }
        String imgPATH = bundle.getString(NotesDatabaseConstants.IMGPATH);
        if (! imgPATH.equals(""))
        {
            File imgFile = new File(bundle.getString(NotesDatabaseConstants.IMGPATH));

            if (imgFile.exists()) {
                Picasso.with(this).load(imgFile).into(image);
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_button:
                NotesDatabaseHandler db = new NotesDatabaseHandler(this);
                db.deleteNote(id);
                cancelAlarm(id);

                finish();
                Intent intent = new Intent(this,OnScreenActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void cancelAlarm(int ID)
    {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReceiver.class);
        intent.putExtra(NotesDatabaseConstants.REMINDER_ID,Integer.toString(ID));
        PendingIntent pendingIntent =PendingIntent.getBroadcast(this,ID,intent,0);
        alarmManager.cancel(pendingIntent);
    }
}
