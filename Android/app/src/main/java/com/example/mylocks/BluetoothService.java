package com.example.mylocks;
// Poate duce un singur task la o anumita perioada (cautare continua)

import android.app.IntentService;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.BluetoothCallback;
import me.aflak.bluetooth.interfaces.DiscoveryCallback;

public class BluetoothService extends IntentService {
    public BluetoothService(String name) {
        super(name);
    }
    boolean waitToUnlock = false;

    Bluetooth bluetooth;

    // Intoarce parola numai in cazul in care e autolock, altfel null
    String getPassword(String address) {
        SharedPreferences sharedpreferencesautlocks = getSharedPreferences("AddressAutolog", Context.MODE_PRIVATE);
        boolean isAutolock = sharedpreferencesautlocks.getBoolean(address, false);
        if (isAutolock) {
            SharedPreferences sharedpreferenceslocks = getSharedPreferences("AddressToPassword", Context.MODE_PRIVATE);
            String password = sharedpreferenceslocks.getString(address, null);
            return password;
        }
        return null;
    }

    // Send unlock
    void sendUnlock(BluetoothDevice device) {
        bluetooth.send(new byte[]{2});
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
                String address = device.getAddress();
                String password = getPassword(address);

                if(password != null) {
                    List<BluetoothDevice> paired = bluetooth.getPairedDevices();
                    if (paired.contains(device)) {
                        bluetooth.pair(device);
                    } else {
                        bluetooth.pair(device, password);
                    }
                    waitToUnlock = true;
                }
            }
            @Override public void onDevicePaired(BluetoothDevice device) {
                if (waitToUnlock) sendUnlock(device);
                waitToUnlock = false;
            }
            @Override public void onDeviceUnpaired(BluetoothDevice device) {}
            @Override public void onError(int errorCode) {}
        });
        bluetooth.startScanning();
    }
}
