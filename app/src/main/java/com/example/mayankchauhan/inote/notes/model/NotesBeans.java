package com.example.mayankchauhan.inote.notes.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mayankchauhan on 31/05/17.
 */

public class NotesBeans implements Parcelable {
    private int iD;
    private String title;
    private String note;
    private String imgPath;
    private String mDate;
    private String mTime;

    public NotesBeans() {
    }

    public NotesBeans(int iD, String title, String note, String imgPath, String mDate, String mTime) {
        this.iD = iD;
        this.title = title;
        this.note = note;
        this.imgPath = imgPath;
        this.mDate = mDate;
        this.mTime = mTime;
    }

    public NotesBeans(String title, String note, String imgPath, String mDate, String mTime) {
        this.title = title;
        this.note = note;
        this.imgPath = imgPath;
        this.mDate = mDate;
        this.mTime = mTime;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    protected NotesBeans(Parcel in) {
        iD = in.readInt();
        title = in.readString();
        note = in.readString();
        imgPath = in.readString();
        mDate = in.readString();
        mTime = in.readString();
    }

    public static final Creator<NotesBeans> CREATOR = new Creator<NotesBeans>() {
        @Override
        public NotesBeans createFromParcel(Parcel in) {
            return new NotesBeans(in);
        }

        @Override
        public NotesBeans[] newArray(int size) {
            return new NotesBeans[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iD);
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(imgPath);
        dest.writeString(mDate);
        dest.writeString(mTime);
    }
}
