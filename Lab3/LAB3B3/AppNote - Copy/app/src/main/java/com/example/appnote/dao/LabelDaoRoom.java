package com.example.appnote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appnote.LabelItem;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface LabelDaoRoom
{
    @Insert
    void insertLabel(LabelItem labelItem);

    @Query("select * from Label")
    List<LabelItem> getListLabel();

    @Delete
    void deleleLabel(LabelItem labelItem);
}
