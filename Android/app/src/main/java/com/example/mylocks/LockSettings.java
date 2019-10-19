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
                    // Some bth thing here

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
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
                    // Some bth thing here

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
        else if (label.equals("delete")) {
            SharedPreferences sharedpreferences = getSharedPreferences("Locks", Context.MODE_PRIVATE);
            sharedpreferences.edit().remove(name).commit();
        }
        else if (label.equals("back")) {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        SharedPreferences sharedpreferences = getSharedPreferences("Autolog", Context.MODE_PRIVATE);
        sharedpreferences.edit().putBoolean(name, b).commit();

    }
}
