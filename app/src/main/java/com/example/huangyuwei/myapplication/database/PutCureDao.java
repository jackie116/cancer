package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by goodweather on 2018/1/14.
 */

@Dao
public interface PutCureDao {

    @Query("SELECT * FROM PutCure")
    List<PutCure> getAllPutCure();

    @Insert
    void insertPutCure(PutCure... PutCures);

    @Update
    void updatePutCure(PutCure... PutCures);

    @Delete
    void delete(PutCure... PutCures);

}
