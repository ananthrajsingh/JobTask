package com.kisannetwork.kisannetwork.Fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kisannetwork.kisannetwork.Activity.ContactDetailActivity;
import com.kisannetwork.kisannetwork.Contact;
import com.kisannetwork.kisannetwork.ContactsAdapter;
import com.kisannetwork.kisannetwork.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ContactsFragment extends Fragment implements ContactsAdapter.ItemClickListener {

    private ArrayList<Contact> mContacts;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ContactsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonString = inputStreamToString(getResources().openRawResource(R.raw.generated));
        mContacts = new ArrayList<>();

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
                //TODO Every contact has my number
                Contact contactObject = new Contact(age, name, gender, email, "918006303375");
                mContacts.add(contactObject);

            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = getView().findViewById(R.id.contacts_recycler_view);
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

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getContext(), mContacts.get(position).getName() + " clicked!" , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), ContactDetailActivity.class);
        intent.putExtra("name", mContacts.get(position).getName());
        intent.putExtra("phone", mContacts.get(position).getPhone());
        intent.putExtra("email", mContacts.get(position).getEmail());
        startActivity(intent);

    }
}
