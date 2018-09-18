package com.kisannetwork.kisannetwork.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by ananthrajsingh on 17/09/18
 * This is Data Access Object interface. We don't have to implement this interface, as at compile time
 * Room will generate implementation whenever it is references by a database.
 *
 * It should be taken care that these queries are done on a separate thread.
 */
@Dao
public interface HistoryDao {
    @Insert
    void insertHistory(History history);

    @Query("SELECT * FROM History ORDER BY time DESC")
    LiveData<List<History>> fetchHistory();
}
