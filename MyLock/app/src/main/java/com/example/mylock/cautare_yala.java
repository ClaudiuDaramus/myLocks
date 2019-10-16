package com.example.mylock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class cautare_yala extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cautare_yala);
    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }
}
