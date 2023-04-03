package com.example.lab4_b3;

public class Item {
    private String name;
    private Boolean check;

    public Item(String name, Boolean check) {
        this.name = name;
        this.check=check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
