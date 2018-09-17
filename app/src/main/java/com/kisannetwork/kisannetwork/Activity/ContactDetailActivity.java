package com.kisannetwork.kisannetwork.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.kisannetwork.kisannetwork.R;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView mNameTv;
    private TextView mPhoneTv;
    private TextView mEmailTv;
    private Button mSendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        getSupportActionBar().setTitle("Detail");

        mNameTv = findViewById(R.id.detail_name_tv);
        mPhoneTv = findViewById(R.id.detail_phone_tv);
        mEmailTv = findViewById(R.id.detail_email_tv);
        mSendMessageButton = findViewById(R.id.detail_send_message_button);

        Intent intent = getIntent();
        mNameTv.setText(intent.getStringExtra("name"));
        mPhoneTv.setText("+" + intent.getStringExtra("phone"));
        mEmailTv.setText(intent.getStringExtra("email"));
    }
}
