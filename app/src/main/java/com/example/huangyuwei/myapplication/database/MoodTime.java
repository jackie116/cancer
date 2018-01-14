package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

import org.w3c.dom.Text;

/**
 * Created by huangyuwei on 2017/12/16.
 */
@Entity(tableName = "moodtime", primaryKeys = {"date_id"})
public class MoodTime {
    public Integer date_id;
    public Integer score;
    public String diary;
}
