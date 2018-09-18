package com.kisannetwork.kisannetwork;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kisannetwork.kisannetwork.Model.History;
import com.kisannetwork.kisannetwork.Model.HistoryDatabase;

import java.util.List;

/**
 * Created by ananthrajsingh on 17/09/18
 *
 * This is ViewModel class.
 *
 * This buddy is the communicator between Repository and our App UI. It's main role is to provide UI
 * with data to be shown.
 *
 * COPIED FROM DOCUMENTATION FOR BETTER UNDERSTANDING :-
 * A ViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration
 * changes. Separating your app's UI data from your Activity and Fragment classes lets you better
 * follow the single responsibility principle: Your activities and fragments are responsible for
 * drawing data to the screen, while your ViewModel can take care of holding and processing all
 * the data needed for the UI.
 */
public class HistoryViewModel extends AndroidViewModel {

    //Getting hold of the Repository
    private HistoryRepository mRepository;
    //Getting hold of all histories
    private LiveData<List<History>> mAllHistory;

    public HistoryViewModel(Application application){
        super(application);

        mRepository = new HistoryRepository(application);

        mAllHistory = mRepository.fetchAllHistory();

    }

    //Getter method, this will provide complete abstraction of implementation from UI
    public LiveData<List<History>> fetchAllHistory(){ return mAllHistory; }
    //Setter, with same description as above
    public void insert(History history){ mRepository.insert(history); }

}
