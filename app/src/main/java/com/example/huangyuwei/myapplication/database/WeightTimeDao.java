package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by huangyuwei on 2017/12/18.
 */
@Dao
public interface WeightTimeDao {
    @Query("SELECT * FROM weighttime")
    List<WeightTime> getAllWeightTime();

    @Insert
    void insertWeightTime(WeightTime... weightTimes);

    @Update
    void updateWeightTime(WeightTime... weightTimes);

    @Delete
    void delete(WeightTime... weightTimes);
}
