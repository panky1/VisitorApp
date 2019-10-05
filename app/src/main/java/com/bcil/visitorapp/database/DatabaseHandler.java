package com.bcil.visitorapp.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bcil.visitorapp.model.VisitorData;


/**
 * Created by NG on 20-Jul-2017.
 */
@Database(entities = {VisitorData.class}, version = 1)
public abstract class DatabaseHandler extends RoomDatabase {
    public abstract DaoAccess daoAccess();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
}

