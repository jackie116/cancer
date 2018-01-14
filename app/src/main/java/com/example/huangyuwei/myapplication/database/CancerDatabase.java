package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import org.junit.After;

import java.io.IOException;

/**
 * Created by tom on 26/11/2017.
 */

@Database(entities = {FoodTime.class,MoodTime.class,TmpTime.class, ChemCure.class,PutCure.class,WeightTime.class,BloodTime.class}, version = 14)

public abstract class CancerDatabase extends RoomDatabase {

    private static CancerDatabase INSTANCE;
    public abstract FoodTimeDao foodTimeDao();
    public abstract MoodTimeDao moodTimeDao();
    public abstract ChemCureDao chemCureDao();
    public abstract WeightTimeDao weightTimeDao();
    public abstract PutCureDao putCureDao();
    public abstract TmpTimeDao tmpTimeDao();
    public abstract BloodTimeDao bloodTimeDao();
    public static CancerDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,CancerDatabase.class,"cancerdatabase")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_1_4).allowMainThreadQueries().build();

        }
        return INSTANCE;
    }

    @After
    public void closeDb() throws IOException {
        INSTANCE.close();
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }



    @VisibleForTesting
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };


    @VisibleForTesting
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };


    @VisibleForTesting
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // SQLite supports a limited operations for ALTER.
            // Changing the type of a column is not directly supported, so this is what we need
            // to do:
            // Create the new table

        }
    };

    @VisibleForTesting
    static final Migration MIGRATION_1_4 = new Migration(1, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new table

        }
    };

}