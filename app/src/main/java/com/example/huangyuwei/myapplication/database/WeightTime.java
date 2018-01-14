package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by huangyuwei on 2017/12/18.
 */

@Entity(tableName = "weighttime", primaryKeys = {"date_id"})
public class WeightTime {
    public Integer date_id;
    public Double kg;
    public Double bmi;
    public String result;
}
