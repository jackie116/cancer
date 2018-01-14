package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by huangyuwei on 2017/12/18.
 */
@Entity(tableName = "tmptime", primaryKeys = {"date_id","time"})
public class TmpTime {
    public Integer date_id;
    public Integer time;
    public Double degree ;
}

