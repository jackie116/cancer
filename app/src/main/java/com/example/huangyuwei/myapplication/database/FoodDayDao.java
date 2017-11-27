package com.example.huangyuwei.myapplication.database;

/**
 * Created by tom on 26/11/2017.
 */
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;
import java.util.List;

@Dao
public interface FoodDayDao {
    @Query("SELECT * FROM foodday")
    List<FoodDay> getAll();

    @Insert
    void insertFoodDay(FoodDay... foodDays);

    @Update
    void updateFoodDay(FoodDay... foodDays);

    @Delete
    void delete(FoodDay... foodDays);
}