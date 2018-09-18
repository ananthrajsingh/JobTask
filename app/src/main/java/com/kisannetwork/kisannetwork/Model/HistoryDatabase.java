package com.kisannetwork.kisannetwork.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by ananthrajsingh on 17/09/18
 */
@Database(entities = { History.class }, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {

    public abstract HistoryDao historyDao();

    private static volatile HistoryDatabase INSTANCE;

    public static HistoryDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (HistoryDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HistoryDatabase.class,
                            "history_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
