package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by huangyuwei on 2018/1/14.
 */
@Entity(tableName = "bloodtime", primaryKeys = {"date_id"})
public class BloodTime {
    public Integer date_id;
    public Double WBC;
    public Double HB;
    public String PLT;
}
