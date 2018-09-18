package com.kisannetwork.kisannetwork;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.kisannetwork.kisannetwork.Model.History;
import com.kisannetwork.kisannetwork.Model.HistoryDao;
import com.kisannetwork.kisannetwork.Model.HistoryDatabase;

import java.util.List;

/**
 * Created by ananthrajsingh on 17/09/18
 */
public class HistoryRepository {
    private HistoryDao mHistoryDao;
    private LiveData<List<History>> mAllHistory;

    HistoryRepository(Application application){
        HistoryDatabase db = HistoryDatabase.getDatabase(application);
        mHistoryDao = db.historyDao();
        mAllHistory = mHistoryDao.fetchHistory();
    }

    LiveData<List<History>> fetchAllHistory(){
        return mAllHistory;
    }

    public void insert( History history ){
        new insertAsyncTask(mHistoryDao).execute(history);
    }


    private static class insertAsyncTask extends AsyncTask<History, Void, Void>{

        private HistoryDao mAsyncTaskDao;

        insertAsyncTask(HistoryDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            mAsyncTaskDao.insertHistory(histories[0]);
            return null;
        }
    }
}
