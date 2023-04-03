package com.example.lab4_b4;

import android.widget.ToggleButton;

public class ToggleList
{
    String name;
    Boolean status;

    public ToggleList(String name,Boolean status) {
        this.name=name;
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
