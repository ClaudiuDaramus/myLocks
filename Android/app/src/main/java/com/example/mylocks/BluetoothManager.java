package com.example.mylocks;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.BluetoothCallback;
import me.aflak.bluetooth.interfaces.DeviceCallback;
import me.aflak.bluetooth.interfaces.DiscoveryCallback;

public class BluetoothManager {
    private static final String TAG = "BluetoothManager";

    private static BluetoothManager ourInstance = null;

    Bluetooth bluetooth;

    Context nowContext;

    public static BluetoothManager getInstance() {
        return ourInstance;
    }

    public BluetoothManager(Context context) {
        ourInstance = this;
        nowContext = context;

        bluetooth = new Bluetooth(context);

        bluetooth = new Bluetooth(context);
        bluetooth.setBluetoothCallback(new BluetoothCallback() {
            @Override public void onBluetoothTurningOn() {}
            @Override public void onBluetoothTurningOff() {}
            @Override public void onBluetoothOff() {}
            @Override public void onUserDeniedActivation() {}
            @Override public void onBluetoothOn() {}
        });

        bluetooth.setDeviceCallback(new DeviceCallback() {
            @Override public void onDeviceConnected(BluetoothDevice device) {}
            @Override public void onDeviceDisconnected(BluetoothDevice device, String message) {}
            @Override public void onMessage(byte[] message) {}
            @Override public void onError(int err) {
                Toast.makeText(nowContext, "Some device connection error occured", Toast.LENGTH_SHORT).show();
            }
            @Override public void onConnectError(BluetoothDevice device, String message) {

            }
        });

        bluetooth.setDiscoveryCallback(new DiscoveryCallback() {
            @Override public void onDiscoveryStarted() {}
            @Override public void onDiscoveryFinished() {}
            @Override public void onDeviceFound(BluetoothDevice device) {}
            @Override public void onDevicePaired(BluetoothDevice device) {}
            @Override public void onDeviceUnpaired(BluetoothDevice device) {}
            @Override public void onError(int err) {
                Toast.makeText(nowContext, "Can't find device", Toast.LENGTH_SHORT).show();
            }
        });

        onStart();
    }

    Context newLocks;
    public void fillLocks(final SearchLock activity) {
        BluetoothManager.getInstance().scan();
        bluetooth.setDiscoveryCallback(new DiscoveryCallback() {
            @Override public void onDiscoveryStarted() {}
            @Override public void onDiscoveryFinished() {}
            @Override public void onDeviceFound(BluetoothDevice device) {
                if (activity.isFinishing())
                    return;
                activity.addLock(device.getName(), device.getAddress());
            }
            @Override public void onDevicePaired(BluetoothDevice device) {}
            @Override public void onDeviceUnpaired(BluetoothDevice device) {}
            @Override public void onError(int err) {
                Toast.makeText(nowContext, "Can't find device", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void scan() {
        bluetooth.startScanning();
    }

    public void connectToAddress(String address, String password) {
        int ok = 0;
        List<BluetoothDevice> paired = bluetooth.getPairedDevices();

        for(BluetoothDevice device : paired){
            if(device.getAddress().equals(address)) {
                if (password.equals("")){
                    bluetooth.pair(device);
                }
                else {
                    bluetooth.pair(device, password);
                }
                ok = 1;
                break;
            }
        }

        if(ok == 0) {
            Toast.makeText(nowContext, "Error finding paired device", Toast.LENGTH_SHORT).show();
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
