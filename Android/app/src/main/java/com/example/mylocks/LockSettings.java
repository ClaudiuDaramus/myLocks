package com.example.mylocks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class LockSettings extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    String name;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locksettings);

        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("name");
        address = bundle.getString("address");

        Switch autoSwitch = (Switch) findViewById(R.id.switch1);
        autoSwitch.setOnCheckedChangeListener(this);

        TextView title = (TextView) findViewById(R.id.textView2);
        title.setText(name);

        setContentView(R.layout.activity_searchlock);
        String address = savedInstanceState.getString("address");
        BluetoothManager manager = BluetoothManager.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("AddressToPassword", Context.MODE_PRIVATE);
        String password = sharedPreferences.getString(address, "");
        manager.connectToAddress(address, password);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String label = button.getText().toString();
        if (label.equals("change pin")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New PIN");

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String password = input.getText().toString();

                    SharedPreferences sharedpreferences = getSharedPreferences("AddressToPassword", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(address);
                    editor.putString(address, password);
                    editor.commit();
                    // TODO change pass
                    BluetoothManager manager = BluetoothManager.getInstance();
                    manager.resetPIN(password);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        else if (label.equals("change name")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New name");

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String password = input.getText().toString();

                    SharedPreferences sharedpreferences = getSharedPreferences("NameToAddress", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(name);
                    editor.putString(name, address);
                    editor.commit();

                    sharedpreferences = getSharedPreferences("AddressToName", Context.MODE_PRIVATE);
                    editor = sharedpreferences.edit();
                    editor.remove(address);
                    editor.putString(address, name);
                    editor.commit();

                    BluetoothManager manager = BluetoothManager.getInstance();
                    manager.resetName(password);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        else if (label.equals("delete")) {
            SharedPreferences sharedpreferences = getSharedPreferences("NameToAddress", Context.MODE_PRIVATE);
            String address = sharedpreferences.getString(name, "");
            sharedpreferences.edit().remove(name).commit();

            sharedpreferences = getSharedPreferences("AddressToName", Context.MODE_PRIVATE);
            sharedpreferences.edit().remove(address).commit();

            sharedpreferences = getSharedPreferences("AddressToPassword", Context.MODE_PRIVATE);
            sharedpreferences.edit().remove(address).commit();

            sharedpreferences = getSharedPreferences("AddressAutolog", Context.MODE_PRIVATE);
            sharedpreferences.edit().remove(address).commit();
        }
        else if (label.equals("back")) {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        SharedPreferences sharedpreferences = getSharedPreferences("AddressAutolog", Context.MODE_PRIVATE);
        sharedpreferences.edit().putBoolean(address, b).commit();
    }
}
