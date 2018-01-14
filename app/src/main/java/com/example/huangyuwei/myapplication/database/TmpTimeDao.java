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
public interface TmpTimeDao {
    @Query("SELECT * FROM tmptime")
    List<TmpTime> getAllTmpTime();

    @Insert
    void insertTmpTime(TmpTime... tmpTimes);

    @Update
    void updateTmpTime(TmpTime... tmpTimes);

    @Delete
    void delete(TmpTime... tmpTimes);
}
