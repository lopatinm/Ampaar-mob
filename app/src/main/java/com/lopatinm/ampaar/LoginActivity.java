package com.lopatinm.ampaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    String loginPhone;
    SharedPreferences sPref;
    private static final String MY_SETTINGS = "my_settings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button)findViewById(R.id.loginButton);
        EditText phone = (EditText)findViewById(R.id.editTextPhone);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPhone = phone.getText().toString();
                new SendLoginData().execute();
            }
        });
    }

    class SendLoginData extends AsyncTask<Void, Void, Void> {

        String resultString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "http://api.ampaar.ru/v1/user/registration";
                String parammetrs = "phone="+loginPhone+"&fullname=333";
                byte[] data = null;
                InputStream is = null;

                try {
                    URL url = new URL(myURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    conn.setRequestProperty("Content-Length", "" + Integer.toString(parammetrs.getBytes().length));
                    OutputStream os = conn.getOutputStream();
                    data = parammetrs.getBytes("UTF-8");
                    os.write(data);
                    data = null;

                    conn.connect();
                    int responseCode= conn.getResponseCode();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    if (responseCode == 200) {
                        is = conn.getInputStream();

                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, bytesRead);
                        }
                        data = baos.toByteArray();
                        resultString = new String(data, "UTF-8");
                    }

                } catch (MalformedURLException e) {

                    resultString = "MalformedURLException:" + e.getMessage();
                } catch (IOException e) {

                    resultString = "IOException:" + e.getMessage();
                } catch (Exception e) {

                    resultString = "Exception:" + e.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(resultString != null) {
                Log.i("internet", resultString);
                sPref = getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor e = sPref.edit();
                try {
                    JSONObject stringObject = new JSONObject(resultString);
                    JSONObject stringObjectData = stringObject.getJSONObject("data");
                    String userPhone = stringObjectData.getString("phone");
                    String userToken = stringObjectData.getString("token");
                    String userId = stringObjectData.getString("id");
                    e.putString("userPhone", userPhone);
                    e.putString("userToken", userToken);
                    e.putString("userId", userId);
                    Log.i("PHONE", userToken);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
                e.putBoolean("isLogin", true);
                e.apply();
                e.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }

        }
    }
}

