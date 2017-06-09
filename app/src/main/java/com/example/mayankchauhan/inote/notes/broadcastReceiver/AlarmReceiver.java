package com.example.mayankchauhan.inote.notes.broadcastReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;

import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.notes.model.NotesBeans;
import com.example.mayankchauhan.inote.notes.database.NotesDatabaseConstants;
import com.example.mayankchauhan.inote.notes.database.NotesDatabaseHandler;
import com.example.mayankchauhan.inote.onscreen.activity.OnClickNoteActivity;
import com.example.mayankchauhan.inote.onscreen.activity.OnScreenActivity;

/**
 * Created by mayankchauhan on 05/06/17.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String receivedId = intent.getStringExtra(NotesDatabaseConstants.REMINDER_ID);
        int mReceivedID = Integer.parseInt(receivedId);

        NotesDatabaseHandler db = new NotesDatabaseHandler(context);
        NotesBeans beans = db.getSingleNote(mReceivedID);

        Bundle bundle = new Bundle();
        bundle.putInt(NotesDatabaseConstants.ID,beans.getiD());
        bundle.putString(NotesDatabaseConstants.NOTE,beans.getNote());
        bundle.putString(NotesDatabaseConstants.TITLE,beans.getTitle());
        bundle.putString(NotesDatabaseConstants.IMGPATH,beans.getImgPath());
        bundle.putString(NotesDatabaseConstants.NOTES_DATE,beans.getmDate());
        bundle.putString(NotesDatabaseConstants.NOTES_TIME,beans.getmTime());


        Intent mIntent = new Intent(context,OnClickNoteActivity.class);
        mIntent.putExtra(OnScreenActivity.BUNDLE_NOTE,bundle);

        PendingIntent mClick = PendingIntent.getActivity(context,mReceivedID,mIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle(beans.getTitle());
        builder.setContentText(beans.getNote());
        builder.setTicker(beans.getNote());
        builder.setContentIntent(mClick);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(mReceivedID,builder.build());
    }

}
