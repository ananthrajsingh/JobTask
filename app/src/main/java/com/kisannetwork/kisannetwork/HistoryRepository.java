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
 *
 * This is the Repository class.
 *
 * This provides abstraction to multiple data sources. For example, this class decides whether
 * we will use local database for a query or use network.
 *
 * Honestly, we don't need this class for this application as of now, but this is a good practice,
 * therefore it is being followed.
 */
public class HistoryRepository {
    private HistoryDao mHistoryDao;
    private LiveData<List<History>> mAllHistory;

    HistoryRepository(Application application){
        //Handling database
        HistoryDatabase db = HistoryDatabase.getDatabase(application);
        mHistoryDao = db.historyDao();
        mAllHistory = mHistoryDao.fetchHistory();
    }

    //Wrapper for fetchAllHistory
    LiveData<List<History>> fetchAllHistory(){
        return mAllHistory;
    }

    /**
     * Wrapper of insert. This must be done on background thread else this application will crash
     * Room ensures that we don't do long running task on UI thread.
     * @param history object to be inserted
     */
    public void insert( History history ){
        new insertAsyncTask(mHistoryDao).execute(history);
    }


    /**
     * Nothing special here, regular AsyncTask.
     */
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
