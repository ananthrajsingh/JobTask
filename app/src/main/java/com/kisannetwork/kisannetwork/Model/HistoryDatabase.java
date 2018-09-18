package com.kisannetwork.kisannetwork.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by ananthrajsingh on 17/09/18
 *
 * This is our database layer on top of the SQLite database. Room will take care of this, we don't have
 * to create SQLiteOpenHelper class.
 *
 * We should and must use only one instance of this class throughout our application
 */
@Database(entities = { History.class }, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {

    public abstract HistoryDao historyDao();

    private static volatile HistoryDatabase INSTANCE;

    public static HistoryDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (HistoryDatabase.class){
                if(INSTANCE == null){
                    /*
                     *******************************************************************************
                     ************* Building the database with name history_database ****************
                     *******************************************************************************
                     */
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HistoryDatabase.class,
                            "history_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
