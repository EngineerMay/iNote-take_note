package com.example.mayankchauhan.inote.checklist.broadcast;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.mayankchauhan.inote.R;
import com.example.mayankchauhan.inote.checklist.database.CheckListDatabaseConstants;
import com.example.mayankchauhan.inote.checklist.database.CheckListDatabaseHandler;
import com.example.mayankchauhan.inote.checklist.model.CheckListBeans;
import com.example.mayankchauhan.inote.onscreen.activity.OnClickCheckListActivity;
import com.example.mayankchauhan.inote.onscreen.activity.OnClickNoteActivity;
import com.example.mayankchauhan.inote.onscreen.activity.OnScreenActivity;

/**
 * Created by mayankchauhan on 07/06/17.
 */

public class Alarm extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String str = intent.getStringExtra(CheckListDatabaseConstants.CHECKLIST_REMINDER_ID);
        int id = Integer.parseInt(str);

        CheckListDatabaseHandler db = new CheckListDatabaseHandler(context);
        CheckListBeans beans = db.getSingleCheckList(id);

        Bundle bundle = new Bundle();
        bundle.putInt(CheckListDatabaseConstants.CHECKLIST_ID,id);
        bundle.putString(CheckListDatabaseConstants.CHECKLIST_NAME,beans.getCheckListName());
        bundle.putString(CheckListDatabaseConstants.CHECKLIST_TASK,beans.getCheckListTasks());
        bundle.putString(CheckListDatabaseConstants.CHECKLIST_DATE,beans.getCheckListDate());
        bundle.putString(CheckListDatabaseConstants.CHECKLIST_TIME,beans.getCheckListTime());

        Intent i = new Intent(context, OnClickCheckListActivity.class);
        i.putExtra(OnScreenActivity.BUNDLE_LIST,bundle);

        PendingIntent mClick = PendingIntent.getActivity(context,id,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(beans.getCheckListName());
        builder.setContentIntent(mClick);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id,builder.build());
    }
}
