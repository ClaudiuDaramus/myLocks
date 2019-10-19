package com.example.mylocks;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchLock extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlock);
    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }

    public void addLock(String name, String address) {
        FoundLockView lock = new FoundLockView(this, name, address);
        LinearLayout layout = findViewById(R.id.newLocks);
        layout.addView(lock.getLayout());
    }
}
