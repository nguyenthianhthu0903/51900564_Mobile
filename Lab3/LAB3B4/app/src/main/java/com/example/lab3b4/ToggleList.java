package com.example.lab3b4;

import android.widget.ToggleButton;

public class ToggleList {
    public ToggleList() {
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    public void setToggleButton(ToggleButton toggleButton) {
        this.toggleButton = toggleButton;
    }

    public ToggleList(ToggleButton toggleButton) {
        this.toggleButton = toggleButton;
    }

    ToggleButton toggleButton;
}
