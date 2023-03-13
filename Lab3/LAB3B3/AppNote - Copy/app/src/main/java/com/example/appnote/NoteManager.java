package com.example.appnote;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "TABLENOTE")


//thiếu video,noti,alarm
public class NoteManager implements Serializable{
    @PrimaryKey(autoGenerate
            = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name ="datetime")
    private String dateTime;

    @ColumnInfo(name="subtitle")
    private String subTitle;

    @ColumnInfo(name="notemain")
    private String noteMain;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "img")
    private String imagePath;

    @ColumnInfo(name="video")
    private String videoPath;

    @ColumnInfo(name = "pinned")
    private int pinned;

    @ColumnInfo(name = "eventdate")
    private String eventDate;

    @ColumnInfo(name = "eventtime")
    private String eventTime;

    @ColumnInfo(name = "messNoti")
    private String messNoti;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "alarm")
    private int alarm;

    @ColumnInfo(name = "label")
    private String label;

    @ColumnInfo(name = "lock")
    private int lock;

    @ColumnInfo(name="audio")
    private String audioPath;

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public NoteManager() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getNoteMain() {
        return noteMain;
    }

    public void setNoteMain(String noteMain) {
        this.noteMain = noteMain;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getMessNoti() {
        return messNoti;
    }

    public void setMessNoti(String messNoti) {
        this.messNoti = messNoti;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock)
    {
        this.lock = lock;
    }

    @NonNull
    @Override
    public String toString() {
        return "title= " + title+", dateTime= "+dateTime;
    }


}
