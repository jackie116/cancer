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
    List<ChemCure> getAllChemCure();

    @Insert
    void insertChemCure(ChemCure... ChemCures);

    @Update
    void updateChemCure(ChemCure... ChemCures);

    @Delete
    void delete(ChemCure... ChemCures);

}
