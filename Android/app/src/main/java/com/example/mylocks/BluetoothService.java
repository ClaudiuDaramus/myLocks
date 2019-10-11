package com.example.mylocks;
// Poate duce un singur task la o anumita perioada (cautare continua)

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class BluetoothService extends IntentService {
    public BluetoothService(String name) {
        super(name);
    }

    // This is called on autolaunch
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Gets data from the incoming Intent
        String dataString = intent.getDataString();
        // Do work here, based on the contents of dataString
        // Sau doar codul pentru cautare continua si conectare
    }
}
