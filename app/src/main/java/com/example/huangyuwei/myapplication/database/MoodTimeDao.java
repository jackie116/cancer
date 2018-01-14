package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by huangyuwei on 2017/12/16.
 */
@Dao
public interface MoodTimeDao {
    @Query("SELECT * FROM moodtime")
    List<MoodTime> getAllMoodTime();

    @Insert
    void insertMoodTime(MoodTime... moodTimes);

    @Update
    void updateMoodTime(MoodTime... moodTimes);

    @Delete
    void delete(MoodTime... moodTimes);
}
