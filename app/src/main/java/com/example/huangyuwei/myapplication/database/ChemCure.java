package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by user-pc on 2017/12/17.
 */
@Entity(tableName = "ChemCure", primaryKeys = {"date_id"})
public class ChemCure {
    public int date_id;
    public String cure;
}
