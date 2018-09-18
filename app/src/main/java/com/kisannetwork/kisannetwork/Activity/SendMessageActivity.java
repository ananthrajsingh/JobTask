package com.kisannetwork.kisannetwork.Activity;

import android.arch.lifecycle.ViewModelProviders;
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

public class SendMessageActivity extends AppCompatActivity {


    private TextView mNameTextView;
    private EditText mMessageEditText;
    private Button mSendButton;
    private FrameLayout mTrasparentFrame;
    private ProgressBar mProgressBar;
    private String mOtp;
    private String mName;
    private boolean mFlag;
    private boolean mIsExceptionThrown;
    private static String ACCOUNT_SID = "AC31cbd976585bf0c13d479d6c008bcf41";
    private static String AUTH_TOKEN = "0d884b52c7ed7c1bcc18c4e3e9c9fa7a";

    private HistoryViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        getSupportActionBar().setTitle("Compose message");
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        final String phone = intent.getStringExtra("phone");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);


        mNameTextView = findViewById(R.id.message_name_textview);
        mMessageEditText = findViewById(R.id.message_edittext);
        mSendButton = findViewById(R.id.final_send_message_button);
        mTrasparentFrame = findViewById(R.id.tranparent_frame);
        mProgressBar = findViewById(R.id.send_message_progressbar);
        mProgressBar.setVisibility(GONE);
        mTrasparentFrame.setVisibility(GONE);

        mNameTextView.setText(mName);
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

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }


    private void sendSMS(String phone, String message){
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(
                "https://api.twilio.com/2010-04-01/Accounts/AC31cbd976585bf0c13d479d6c008bcf41/SMS/Messages");
        String base64EncodedCredentials = "Basic "
                + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                Base64.NO_WRAP);

        httppost.setHeader("Authorization",
                base64EncodedCredentials);
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



//            // Execute HTTP Post Request
//            HttpResponse response = httpclient.execute(httppost);
//            HttpEntity entity = response.getEntity();
////            System.out.println("Entity post is: "
////                    + EntityUtils.toString(entity));
//            Log.e("http log ", "Entity post is: "
//                    + EntityUtils.toString(entity));
//
//            mProgressBar.setVisibility(GONE);
//            mTrasparentFrame.setVisibility(GONE);
//            mMessageEditText.setText("");
//            Toast.makeText(getBaseContext(), "Message sent", Toast.LENGTH_LONG).show();


//        } catch (ClientProtocolException e) {

            SendMessageAsyncTask asyncTask = new SendMessageAsyncTask(httpclient, httppost);
            asyncTask.execute();

        } catch (IOException e) {
            e.printStackTrace();
            mFlag = true;
        }

        if (!mFlag){
            long time = System.currentTimeMillis();
            History history = new History(mName, mOtp, time);
            mViewModel.insert(history);
            Toast.makeText(this, "Message sent!", Toast.LENGTH_LONG).show();
        }
        else{
            mFlag = false;

        }


        mProgressBar.setVisibility(GONE);
        mTrasparentFrame.setVisibility(GONE);
    }

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
//            System.out.println("Entity post is: "
//                    + EntityUtils.toString(entity));
            Log.e("http log ", "Entity post is: "
                    + EntityUtils.toString(entity));
            }
            catch (Exception e){
                e.printStackTrace();
                mIsExceptionThrown = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mProgressBar.setVisibility(GONE);
            mTrasparentFrame.setVisibility(GONE);
            mMessageEditText.setText("");
            if (mIsExceptionThrown){
                //Message not sent
                Toast.makeText(getBaseContext(), "Failed!", Toast.LENGTH_LONG).show();

            }
//            Toast.makeText(getBaseContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }

}
