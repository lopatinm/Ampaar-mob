package com.lopatinm.ampaar.ui.barter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lopatinm.ampaar.R;
import com.lopatinm.ampaar.ui.ampaar.Product;
import com.lopatinm.ampaar.ui.ampaar.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BarterFragment extends Fragment {

    static View root;
    static ArrayList<Barter> barters = new ArrayList<Barter>();

    static SharedPreferences sPref;
    private static final String MY_SETTINGS = "my_settings";
    static String token;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_barter, container, false);
        sPref = this.requireContext().getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        token = sPref.getString("userToken", "");
        new SendBarterData().execute();
        return root;
    }

    static class SendBarterData extends AsyncTask<Void, Void, Void> {

        String resultString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "http://api.ampaar.ru/v1/barter";
                byte[] data = null;
                InputStream is = null;

                try {
                    URL url = new URL(myURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", "Bearer " + token);

                    conn.connect();
                    int responseCode= conn.getResponseCode();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Log.i("dsfgdsgdfgdfgdfgd", " "+responseCode);
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


                try {
                    JSONArray stringArray = new JSONArray(resultString);
                    barters.clear();
                    for (int i=0; i < stringArray.length(); i++) {
                        barters.add(new Barter (stringArray.getJSONObject(i).getInt("id"), stringArray.getJSONObject(i).getInt("product_id"), stringArray.getJSONObject(i).getString("comment"), stringArray.getJSONObject(i).getInt("count"), stringArray.getJSONObject(i).getString("name") ));
                    }

                    RecyclerView recyclerView = root.findViewById(R.id.recyclerViewBarter);
                    BarterAdapter adapter = new BarterAdapter(root.getContext(), barters);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }

            }

        }
    }
}

