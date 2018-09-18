package com.kisannetwork.kisannetwork.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisannetwork.kisannetwork.Adapter.HistoryAdapter;
import com.kisannetwork.kisannetwork.HistoryViewModel;
import com.kisannetwork.kisannetwork.Model.Contact;
import com.kisannetwork.kisannetwork.Model.History;
import com.kisannetwork.kisannetwork.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

//    private ArrayList<History> mHistory;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private HistoryViewModel mViewModel;

    public HistoryFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_history_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.history_recyclerView);

        mAdapter = new HistoryAdapter(getContext());

        /*
         * This is done here because in onCreate mRecyclerView will throw NullPointerException.
         *
         * This is because we cannot call findViewById in onCreate, so by the time we do
         * operations on mRecyclerView, it is not even connected to its xml counterpart, thus
         * throwing the exception.
         */
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        mViewModel.fetchAllHistory().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(@Nullable List<History> histories) {
                mAdapter.setHistory(histories);
            }
        });

    }
}
