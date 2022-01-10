package com.example.mytractor;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class DataAddedActivity extends AppCompatActivity {
    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_added);
        Objects.requireNonNull(getSupportActionBar()).hide();
        h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(DataAddedActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        },5000);


    }
}