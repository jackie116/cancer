package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by huangyuwei on 2017/12/10.
 */
@Entity(tableName = "tmptime", primaryKeys = {"date_id"})
public class TmpTime {
    public int date_id;
    public int date;
    public int time;
    public double degree;
}