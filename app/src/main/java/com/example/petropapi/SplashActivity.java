package com.example.petropapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // Duration in milliseconds (2 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Optionally, if you want to use a layout, you can call setContentView(R.layout.activity_splash);
        // Otherwise, the splash theme's windowBackground will be used.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start MainActivity and finish this splash activity
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, SPLASH_DURATION);
    }
}
