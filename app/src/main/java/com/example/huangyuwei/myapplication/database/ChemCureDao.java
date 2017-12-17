package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by user-pc on 2017/12/17.
 */
@Dao
public interface ChemCureDao {
    @Query("SELECT * FROM ChemCure")
    List<ChemCure> getAllFoodTime();

    @Insert
    void insertFoodTime(ChemCure... ChemCures);

    @Update
    void updateFoodTime(ChemCure... ChemCures);

    @Delete
    void delete(ChemCure... ChemCures);

}
