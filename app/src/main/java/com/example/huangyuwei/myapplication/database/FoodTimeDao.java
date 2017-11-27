package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by tom on 27/11/2017.
 */

@Dao
public interface FoodTimeDao {

    @Query("SELECT * FROM foodtime")
    List<FoodTime> getAllFoodTime();

    @Insert
    void insertFoodTime(FoodTime... foodTimes);

    @Update
    void updateFoodTime(FoodTime... foodTimes);

    @Delete
    void delete(FoodTime... foodTimes);
}
