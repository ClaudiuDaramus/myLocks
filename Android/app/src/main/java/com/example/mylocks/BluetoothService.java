package com.example.mylocks;
// Poate duce un singur task la o anumita perioada (cautare continua)

import android.app.IntentService;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.Map;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.BluetoothCallback;
import me.aflak.bluetooth.interfaces.DiscoveryCallback;

public class BluetoothService extends IntentService {
    public BluetoothService(String name) {
        super(name);
    }

    Bluetooth bluetooth;

    // Intoarce parola numai in cazul in care e autolock, altfel null
    String getPassword(String name) {
        SharedPreferences sharedpreferencesautlocks = getSharedPreferences("Autolog", Context.MODE_PRIVATE);
        boolean isAutolock = sharedpreferencesautlocks.getBoolean(name, false);
        if (isAutolock) {
            SharedPreferences sharedpreferenceslocks = getSharedPreferences("Locks", Context.MODE_PRIVATE);
            String password = sharedpreferenceslocks.getString(name, null);
            return password;
        }
        return null;
    }

    // This is called on autolaunch
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Gets data from the incoming Intent
        String dataString = intent.getDataString();
        // Do work here, based on the contents of dataString
        // Sau doar codul pentru cautare continua si conectare
        bluetooth = new Bluetooth(getApplicationContext());
        bluetooth.setDiscoveryCallback(new DiscoveryCallback() {
            @Override public void onDiscoveryStarted() {}
            @Override public void onDiscoveryFinished() {}
            @Override public void onDeviceFound(BluetoothDevice device) {
                String name = device.getName();
                String address = device.getAddress();
            }
            @Override public void onDevicePaired(BluetoothDevice device) {}
            @Override public void onDeviceUnpaired(BluetoothDevice device) {}
            @Override public void onError(int errorCode) {}
        });
    }
}
