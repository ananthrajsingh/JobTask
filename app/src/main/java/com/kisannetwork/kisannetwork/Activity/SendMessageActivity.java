package com.kisannetwork.kisannetwork.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Entity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kisannetwork.kisannetwork.HistoryViewModel;
import com.kisannetwork.kisannetwork.Model.History;
import com.kisannetwork.kisannetwork.R;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsClient;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.view.View.GONE;

/**
 * This Activity is used to Send message to the particular selected contact.
 * Here we will have a pre-generated otp in edittext.
 *
 * Since I'm using trial account of Twilio, it only allows to send message to numbers which are verified.
 *
 */
public class SendMessageActivity extends AppCompatActivity {


    private TextView mNameTextView;
    private EditText mMessageEditText;
    private Button mSendButton;
    private FrameLayout mTrasparentFrame;
    private ProgressBar mProgressBar;
    private String mOtp;
    private String mName;
    private boolean mIsExceptionThrown;
    /*
     * WARNING: This project should not be uploaded to public repository as it contains these Keys.
     * By the way, this account is trial account for now. It would be more of the threat if we get
     * paid account.
     */
    private static String ACCOUNT_SID = "AC31cbd976585bf0c13d479d6c008bcf41";
    private static String AUTH_TOKEN = "0d884b52c7ed7c1bcc18c4e3e9c9fa7a";

    private HistoryViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        getSupportActionBar().setTitle("Compose message");
        //This is coming from ContactDetailActivity
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        final String phone = intent.getStringExtra("phone");

        //Below code was used earlier when not using AsyncTask, it would forcefully do network
        //operation on UI thread

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        StrictMode.setThreadPolicy(policy);


        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);


        mNameTextView = findViewById(R.id.message_name_textview);
        mMessageEditText = findViewById(R.id.message_edittext);
        mSendButton = findViewById(R.id.final_send_message_button);
        mTrasparentFrame = findViewById(R.id.tranparent_frame);
        mProgressBar = findViewById(R.id.send_message_progressbar);
        mProgressBar.setVisibility(GONE);
        mTrasparentFrame.setVisibility(GONE);

        mNameTextView.setText(mName);
        //We need otp to be stored in history
        mOtp = getRandomNumberString();
        String message = "Hi.  Your  OTP  is: " + mOtp;
        mMessageEditText.setText(message);


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mMessageEditText.getText().toString().isEmpty()){
                    mMessageEditText.setError("Cannot be empty");
                    mMessageEditText.requestFocus();
                }
                else{
                    sendSMS("+" + phone, mMessageEditText.getText().toString());
                    mTrasparentFrame.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    /**
     * This is a helper function to generate otp.
     * @return 6 digit OTP
     */
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }


    /**
     * This function is called when user clicks on SEND. Here we are talking to Twilio.
     * We will send the message and check the response.
     *
     * Since this involves network operation, we cannot have it running on UI thread. Thus,
     * AsyncTask is used for the rescue.
     * @param phone number to send message to
     * @param message the message to be sent
     */
    private void sendSMS(String phone, String message){
        HttpClient httpclient = new DefaultHttpClient();

        /*
         * Our app should know who to talk to.
         */
        HttpPost httppost = new HttpPost(
                "https://api.twilio.com/2010-04-01/Accounts/AC31cbd976585bf0c13d479d6c008bcf41/SMS/Messages");
        /*
         * I guess Twilio doesn't talks to everyone, so we will have to follow this procedure.
         */
        String base64EncodedCredentials = "Basic "
                + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                Base64.NO_WRAP);

        httppost.setHeader("Authorization",
                base64EncodedCredentials);
        /*
         * Below we are preparing for our short talk with Twilio.
         */
        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("From",
                    "+18507573167"));
            nameValuePairs.add(new BasicNameValuePair("To",
                    phone));
            Log.e("SendMessageAct", "Phone to send message - " + phone);
            nameValuePairs.add(new BasicNameValuePair("Body",
                    message));

            httppost.setEntity(new UrlEncodedFormEntity(
                    nameValuePairs));


            //Do network task using AsyncTask
            /*
             * Talking to Twilio
             */
            SendMessageAsyncTask asyncTask = new SendMessageAsyncTask(httpclient, httppost);
            asyncTask.execute();

        } catch (IOException e) {
            //Things didn't go as planned
            e.printStackTrace();
        }



        mProgressBar.setVisibility(GONE);
        mTrasparentFrame.setVisibility(GONE);
    }

    //Okay, I am aware this is a memory leak, since this task time bound, I'll get back to this later
    private class SendMessageAsyncTask extends AsyncTask<Void, Void, Void>{

        private HttpClient mHttpClient;
        private HttpPost mHttpPost;

        public SendMessageAsyncTask(HttpClient httpClient, HttpPost httpPost){
            mHttpClient = httpClient;
            mHttpPost = httpPost;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mTrasparentFrame.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                HttpResponse response = mHttpClient.execute(mHttpPost);
                HttpEntity entity = response.getEntity();

            Log.e("http log ", "Entity post is: "
                    + EntityUtils.toString(entity));
            if(EntityUtils.toString(entity).contains("The number  is unverified")){
                mIsExceptionThrown = true;
            }
            }
            catch (Exception e){
                e.printStackTrace();
//                mIsExceptionThrown = true;
//                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mProgressBar.setVisibility(GONE);
            mTrasparentFrame.setVisibility(GONE);
            Log.e("onPostExecute", "mIsExceptionThrown - " + mIsExceptionThrown);
            if (mIsExceptionThrown){
                //Message not sent
                Toast.makeText(getBaseContext(), "Failed! Verify your number.", Toast.LENGTH_LONG).show();
                mIsExceptionThrown = false;
            }
            else{
                /*
                 *************************************************************************************
                 * Storing this message in history_database
                 *************************************************************************************
                 */
                long time = System.currentTimeMillis();
                History history = new History(mName, mOtp, time);
                mViewModel.insert(history);


                mMessageEditText.setText("");
                Toast.makeText(getBaseContext(), "Message sent!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
