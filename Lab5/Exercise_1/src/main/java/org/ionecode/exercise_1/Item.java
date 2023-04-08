package org.ionecode.exercise_1;


import android.widget.CheckBox;

public class Item {

    private String name;
    private CheckBox checkBox;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public boolean getCheckBox() {
        return this.checkBox.isChecked();
    }

    public void setItemCheck(boolean check) {
        this.checkBox.setChecked(check);
    }

}