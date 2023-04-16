package com.example.appnote;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Label")
public class LabelItem
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    String labelName;

    Boolean checkLabel;

    public String getLabelName()
    {
        return labelName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LabelItem(String labelName,Boolean checkLabel)
    {
        this.labelName = labelName;
        this.checkLabel=checkLabel;
    }

    public void setLabelName(String labelName)
    {
        this.labelName = labelName;
    }

    public Boolean getCheckLabel()
    {
        return checkLabel;
    }

    public void setCheckLabel(Boolean checkLabel) {
        this.checkLabel = checkLabel;
    }
}
