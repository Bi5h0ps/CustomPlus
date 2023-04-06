package com.comp7506.customplus.UI.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.comp7506.customplus.R;
import com.comp7506.customplus.UI.splash.SplashActivity;

public class MainActivity extends AppCompatActivity {
    private static final long showTime = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}