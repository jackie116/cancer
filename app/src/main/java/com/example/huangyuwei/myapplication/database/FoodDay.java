package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;
/**
 * Created by tom on 26/11/2017.
 */
@Entity
public class FoodDay {
    @PrimaryKey
    public int date;
}
