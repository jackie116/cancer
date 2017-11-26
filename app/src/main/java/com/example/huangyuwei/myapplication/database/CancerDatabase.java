package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by tom on 26/11/2017.
 */

@Database(entities = {FoodDay.class}, version = 1)
public abstract class CancerDatabase extends RoomDatabase {

    private static CancerDatabase INSTANCE;
    public abstract FoodDayDao foodDayDao();

    public static CancerDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), CancerDatabase.class).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}