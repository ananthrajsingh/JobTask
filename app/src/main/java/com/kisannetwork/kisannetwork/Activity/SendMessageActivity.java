package com.kisannetwork.kisannetwork.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kisannetwork.kisannetwork.R;

import java.util.Random;

public class SendMessageActivity extends AppCompatActivity {


    private TextView mNameTextView;
    private EditText mMessageEditText;
    private Button mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        getSupportActionBar().setTitle("Compose message");
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        mNameTextView = findViewById(R.id.message_name_textview);
        mMessageEditText = findViewById(R.id.message_edittext);
        mSendButton = findViewById(R.id.final_send_message_button);

        mNameTextView.setText(name);
        String otp = getRandomNumberString();
        String message = "Hi.  Your  OTP  is: " + otp;
        mMessageEditText.setText(message);

    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

}
