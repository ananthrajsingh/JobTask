package com.kisannetwork.kisannetwork.Fragments;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisannetwork.kisannetwork.Contact;
import com.kisannetwork.kisannetwork.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    private ArrayList<Contact> mContacts;

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
                Contact contactObject = new Contact(age, name, gender, email, phone);
                mContacts.add(contactObject);
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }

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
}
