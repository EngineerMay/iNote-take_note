package com.example.mayankchauhan.inote.checklist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mayankchauhan on 06/06/17.
 */

public class CheckListBeans implements Parcelable {

    private int checkListId;
    private String checkListName;
    private String checkListTasks;
    private String checkListDate;
    private String checkListTime;

    public CheckListBeans() {
    }

    public CheckListBeans(String checkListName, String checkListTasks, String checkListDate, String checkListTime) {
        this.checkListName = checkListName;
        this.checkListTasks = checkListTasks;
        this.checkListDate = checkListDate;
        this.checkListTime = checkListTime;
    }

    public CheckListBeans(int checkListId, String checkListName, String checkListTasks, String checkListDate, String checkListTime) {
        this.checkListId = checkListId;
        this.checkListName = checkListName;
        this.checkListTasks = checkListTasks;
        this.checkListDate = checkListDate;
        this.checkListTime = checkListTime;
    }

    protected CheckListBeans(Parcel in) {
        checkListId = in.readInt();
        checkListName = in.readString();
        checkListTasks = in.readString();
        checkListDate = in.readString();
        checkListTime = in.readString();
    }

    public int getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(int checkListId) {
        this.checkListId = checkListId;
    }

    public String getCheckListName() {
        return checkListName;
    }

    public void setCheckListName(String checkListName) {
        this.checkListName = checkListName;
    }

    public String getCheckListTasks() {
        return checkListTasks;
    }

    public void setCheckListTasks(String checkListTasks) {
        this.checkListTasks = checkListTasks;
    }

    public String getCheckListDate() {
        return checkListDate;
    }

    public void setCheckListDate(String checkListDate) {
        this.checkListDate = checkListDate;
    }

    public String getCheckListTime() {
        return checkListTime;
    }

    public void setCheckListTime(String checkListTime) {
        this.checkListTime = checkListTime;
    }

    public static final Creator<CheckListBeans> CREATOR = new Creator<CheckListBeans>() {
        @Override
        public CheckListBeans createFromParcel(Parcel in) {
            return new CheckListBeans(in);
        }

        @Override
        public CheckListBeans[] newArray(int size) {
            return new CheckListBeans[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(checkListId);
        dest.writeString(checkListName);
        dest.writeString(checkListTasks);
        dest.writeString(checkListDate);
        dest.writeString(checkListTime);
    }
}
