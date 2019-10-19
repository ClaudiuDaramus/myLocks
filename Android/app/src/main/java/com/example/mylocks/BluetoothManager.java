package com.example.mylocks;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.List;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.BluetoothCallback;
import me.aflak.bluetooth.interfaces.DiscoveryCallback;

public class BluetoothManager {
    private static final String TAG = "BluetoothManager";

    private static final BluetoothManager ourInstance = null;

    Bluetooth bluetooth;

    Context nowContext;

    public static BluetoothManager getInstance() {
        return ourInstance;
    }

    private BluetoothManager(Context context) {
        nowContext = context;

        bluetooth = new Bluetooth(context);

        bluetooth = new Bluetooth(context);
        bluetooth.setBluetoothCallback(bluetoothCallback);

        onStart();
    }

    public void connectToAdress(String address) {
        List<BluetoothDevice> paired = bluetooth.getPairedDevices();

        for(BluetoothDevice device : paired){
            if(device.getAddress().equals(address)) {
                bluetooth.pair(device);
            } else {
                Log.d(TAG, "Lock not found with address.");
            }
        }
    }

    protected void onStart() {
        bluetooth.onStart();
        if(bluetooth.isEnabled()){
            BluetoothCallback bluetoothCallback = null;
            bluetoothCallback.onBluetoothOn();
        } else {
            bluetooth.enable();
        }
    }

    protected void onStop() {
        bluetooth.onStop();
    }

    private BluetoothCallback bluetoothCallback = new BluetoothCallback() {
        @Override public void onBluetoothTurningOn() {}
        @Override public void onBluetoothTurningOff() {}
        @Override public void onBluetoothOff() {}
        @Override public void onUserDeniedActivation() {}
        @Override public void onBluetoothOn() {}
    };

    // Reset PIN
    void resetPIN(String PIN) {
        bluetooth.send(new byte[]{4});
        bluetooth.send(PIN);
    }

    // Reset Name
    void resetName(String Name) {
        bluetooth.send(new byte[]{5});
        bluetooth.send(Name);
    }
}
