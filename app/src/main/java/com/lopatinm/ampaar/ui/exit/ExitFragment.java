package com.lopatinm.ampaar.ui.exit;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lopatinm.ampaar.R;
import com.lopatinm.ampaar.SplashActivity;

public class ExitFragment extends Fragment {

    SharedPreferences sPref;
    private static final String MY_SETTINGS = "my_settings";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sPref = this.getActivity().getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor e = sPref.edit();
        e.putString("userPhone", "");
        e.putString("userToken", "");
        e.putString("userId", "");
        e.putBoolean("isLogin", false);
        e.apply();
        e.commit();

        Intent intent = new Intent(getActivity(), SplashActivity.class);
        startActivity(intent);

        return inflater.inflate(R.layout.fragment_exit, container, false);

    }
}