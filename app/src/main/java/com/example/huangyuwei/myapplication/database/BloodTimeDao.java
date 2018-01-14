package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by huangyuwei on 2018/1/14.
 */
@Dao
public interface BloodTimeDao {
    @Query("SELECT * FROM bloodtime")
    List<BloodTime> getAllBloodTime();

    @Insert
    void insertBloodTime(BloodTime... bloodTimes);

    @Update
    void updateBloodTime(BloodTime... bloodTimes);

    @Delete
    void delete(BloodTime... bloodTimes);
}
