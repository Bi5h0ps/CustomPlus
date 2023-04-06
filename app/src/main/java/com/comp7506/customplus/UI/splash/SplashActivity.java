package com.comp7506.customplus.UI.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.comp7506.customplus.R;
import com.comp7506.customplus.UI.main.MainActivity;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private static final long showTime = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar

        Handler handler = new Handler(getMainLooper());
        Runnable myRunnable= this::jumpToMainActivity;

        handler.postDelayed(myRunnable, showTime*1000);
    }

    private void jumpToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}