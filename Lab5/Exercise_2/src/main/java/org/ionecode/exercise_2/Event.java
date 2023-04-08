package org.ionecode.exercise_2;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SwitchCompat;


public class Event {

    private LinearLayout linearLayout;
    private String title;
    private String place;
    private String date;
    private String time;
    private SwitchCompat _switch;

    public Event(String title, String place, String date, String time) {
        this.title = title;
        this.place = place;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void set_switch(SwitchCompat _switch) {
        this._switch = _switch;
    }

    public boolean isCheck() {
        return this._switch.isChecked();
    }

    public void setCheck(boolean check) {
        this._switch.setChecked(check);
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public LinearLayout getLinearLayout() {
        return this.linearLayout;
    }

    public String toString() {
        return this.title + "_" + this.place + "_" + this.date + "_" + this.time;
    }

}