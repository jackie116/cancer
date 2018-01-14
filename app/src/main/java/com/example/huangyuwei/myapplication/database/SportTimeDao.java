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
public interface SportTimeDao {
    @Query("SELECT * FROM sporttime")
    List<SportTime> getAllSportTime();

    @Insert
    void insertSportTime(SportTime... sportTimes);

    @Update
    void updateSportTime(SportTime... sportTimes);

    @Delete
    void delete(SportTime... sportTimes);
}
