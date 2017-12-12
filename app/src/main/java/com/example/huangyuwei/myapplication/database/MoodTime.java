package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by huangyuwei on 2017/12/3.
 */
@Entity(tableName = "moodtime", primaryKeys = {"date_id", "time"})
public class MoodTime {
    public int date_id;
    public int time;
    public String MoodScore;
    public Double emoji;
}