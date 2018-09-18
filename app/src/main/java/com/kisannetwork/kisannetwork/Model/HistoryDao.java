package com.kisannetwork.kisannetwork.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by ananthrajsingh on 17/09/18
 */
@Dao
public interface HistoryDao {
    @Insert
    void insertHistory(History history);

    @Query("SELECT * FROM History ORDER BY time DESC")
    LiveData<List<History>> fetchHistory();
}
