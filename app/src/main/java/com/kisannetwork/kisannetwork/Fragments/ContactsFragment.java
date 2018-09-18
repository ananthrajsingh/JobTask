package com.kisannetwork.kisannetwork.Fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kisannetwork.kisannetwork.Activity.ContactDetailActivity;
import com.kisannetwork.kisannetwork.Model.Contact;
import com.kisannetwork.kisannetwork.Adapter.ContactsAdapter;
import com.kisannetwork.kisannetwork.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This will be first thing that user sees on application startup. Here we will rig the RecyclerView
 * and set click listeners
 */
public class ContactsFragment extends Fragment implements ContactsAdapter.ItemClickListener {

    private ArrayList<Contact> mContacts;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // We need a default constructor to add fragment to ViewPager
    public ContactsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * Getting json.
         * This file was randomly generated from website json-generator.com.
         * To use real data, in place of getting json from this file, we will use api calls over
         * the network.
         */
        String jsonString = inputStreamToString(getResources().openRawResource(R.raw.generated));
        mContacts = new ArrayList<>();

        //json parsing going on below.
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("contacts");

            for(int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject contact = jsonArray.getJSONObject(i);
                int age = contact.getInt("age");
                String name = contact.getString("name");

                String email = contact.getString("email");
                String gender = contact.getString("gender");
                String phone = contact.getString("phone");
                /*
                 *************************************************************************************
                 * This should not be done this way. For simplicity, so that you can check every name
                 * and see that different names are being stored in history, each contact is being associated
                 * with the number you provided.
                 *
                 * In order to make it work normally, just remove hardwired phone number from below
                 * constructor call and use "phone" variable in it's place
                 * ***********************************************************************************
                 */
                //TODO Correct the hardwired phone number after check is done
                //919971792703
                Contact contactObject = new Contact(age, name, gender, email, "919971792703");
                mContacts.add(contactObject);

            }
            /*
             * Setting a different phone number at last contact.
             * This number is not verified on Twilio. Therefore it can be used to check error message.
             * Which is thrown when number is not a verified number.
             */
            int size = mContacts.size();
            mContacts.get(size - 2).setPhone("918299577148");

        }
        catch (JSONException e){
            e.printStackTrace();
        }



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mRecyclerView = getView().findViewById(R.id.contacts_recycler_view);

        /*
         * This is done here because in onCreate mRecyclerView will throw NullPointerException.
         *
         * This is because we cannot call findViewById in onCreate, so by the time we do
         * operations on mRecyclerView, it is not even connected to its xml counterpart, thus
         * throwing the exception.
         */
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactsAdapter(getContext(), this, mContacts);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_contacts_fragment, container, false);
    }


    /**
     * Helper method to parse input stream to String format.
     * @param inputStream to be handled
     * @return string format of imputStream
     */
    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * This is being implemented to handle click events on recycler view items.
     * This functions knows which item is clicked by the 'position' and thus we can send
     * intent to ContactDetailActivity with relevant details.
     * @param view clicked view
     * @param position position in recycler view
     */
    @Override
    public void onItemClick(View view, int position) {

//        Toast.makeText(getContext(), mContacts.get(position).getName() + " clicked!" , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), ContactDetailActivity.class);
        intent.putExtra("name", mContacts.get(position).getName());
        intent.putExtra("phone", mContacts.get(position).getPhone());
        intent.putExtra("email", mContacts.get(position).getEmail());
        startActivity(intent);

    }
}
