package com.kisannetwork.kisannetwork.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kisannetwork.kisannetwork.Fragments.ContactsFragment;
import com.kisannetwork.kisannetwork.Fragments.HistoryFragment;
import com.kisannetwork.kisannetwork.R;
import com.kisannetwork.kisannetwork.Adapter.ViewPagerAdapter;

public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(new ContactsFragment(), "Contacts");
        pagerAdapter.addFragment(new HistoryFragment(), "History");
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
