package com.example.mayankchauhan.inote.notes.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ImagePickerActivity extends AppCompatActivity {

    private static final int REQUEST_READ_STORAGE = 1;
    private static final int REQUEST_WRITE_STORAGE = 2;
    private static final int ALL_PERMISSION_RESULT = 107;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mayRequestPermission();

        openGallery();
    }
    private void openGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, NoteActivity.RESULT_LOAD_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NoteActivity.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            NoteActivity.selectedImagePath = cursor.getString(columnIndex);
            cursor.close();
        }
        finish();
    }
    @TargetApi(Build.VERSION_CODES.M)
    private boolean mayRequestPermission()
    {
        if (hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && hasPermissions(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            return true;
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            AlertDialog dialog = new AlertDialog.Builder(ImagePickerActivity.this).create();
            dialog.setTitle("Alert");
            dialog.setMessage("App needs to Write to external Storage");
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Dont Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                            ,REQUEST_WRITE_STORAGE);
                }
            });

        }
        else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Alert");
            dialog.setMessage("App needs to Read  external Storage");
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Dont Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                            ,REQUEST_READ_STORAGE);
                }
            });
        }
        else {
            requestPermissions(new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    ,Manifest.permission.READ_EXTERNAL_STORAGE}
                    ,ALL_PERMISSION_RESULT);
        }
        return false;
    }
    private boolean hasPermissions(Context context, String permission)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_STORAGE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Write Permissions Granted", Toast.LENGTH_SHORT).show();
            }
            else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setTitle("Alert");
                dialog.setMessage("App needs to Write to external Storage for full functionality");
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Dont Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                ,REQUEST_WRITE_STORAGE);
                    }
                });
            }
        }
        else if (requestCode == REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Permissions Granted", Toast.LENGTH_SHORT).show();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setTitle("Alert");
                dialog.setMessage("App needs to Read external Storage for full functionality");
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Dont Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                                , REQUEST_READ_STORAGE);
                    }
                });
            }
        }
        else {

            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Alert");
            dialog.setMessage("App needs to read and write external storage");
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Dont allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ,Manifest.permission.READ_EXTERNAL_STORAGE},ALL_PERMISSION_RESULT);
                }
            });
        }
    }
}
