package com.kisannetwork.kisannetwork.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kisannetwork.kisannetwork.R;

/**
 * This Activity is used to show Details of a particular activity when a contact is clicked.
 *
 * This class has mainly self-explanatory stuff, thus has minimal comments. What is happening in here
 * would be easily understandable by any novice Android developer.
 */
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

        /*
         * Getting intent from ContactsFragment
         */
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String phone = intent.getStringExtra("phone");
        mNameTv.setText(name);
        mPhoneTv.setText("+" + phone);
        mEmailTv.setText(intent.getStringExtra("email"));


        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactDetailActivity.this, SendMessageActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
    }
}
