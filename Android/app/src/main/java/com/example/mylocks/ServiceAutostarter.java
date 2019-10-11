package com.example.mylocks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceAutostarter extends BroadcastReceiver {

    private final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(BOOT_COMPLETED_ACTION)){
            Intent myIntent = new Intent(context, com.example.mylocks.BluetoothService.class);
            context.startService(myIntent);
        }
    }
}