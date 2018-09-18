package com.kisannetwork.kisannetwork;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kisannetwork.kisannetwork.Model.History;
import com.kisannetwork.kisannetwork.Model.HistoryDatabase;

import java.util.List;

/**
 * Created by ananthrajsingh on 17/09/18
 */
public class HistoryViewModel extends AndroidViewModel {

    private HistoryRepository mRepository;

    private LiveData<List<History>> mAllHistory;

    public HistoryViewModel(Application application){
        super(application);

        mRepository = new HistoryRepository(application);

        mAllHistory = mRepository.fetchAllHistory();

    }

    public LiveData<List<History>> fetchAllHistory(){ return mAllHistory; }

    public void insert(History history){ mRepository.insert(history); }

}
