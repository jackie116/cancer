package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;
/**
 * Created by tom on 26/11/2017.
 */
@Entity(tableName = "foodtime", primaryKeys = {"date_id", "time"})
public class FoodTime {
    public int date_id;
    public int time;
    public String FoodName;
    public Double calories;
}
