package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by goodweather on 2018/1/14.
 */
@Entity(tableName = "PutCure", primaryKeys = {"time"})
public class PutCure {
    public int time;
    public int start;
    public int end;
    public String place;

}
