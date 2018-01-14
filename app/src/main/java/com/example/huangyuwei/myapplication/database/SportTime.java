package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by huangyuwei on 2018/1/14.
 */
@Entity(tableName = "sporttime", primaryKeys = {"date_id"})
public class SportTime {
    public Integer date_id;
    public String item;
    public Double kcal;
}
