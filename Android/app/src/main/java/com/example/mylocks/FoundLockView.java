package com.example.mylocks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FoundLockView implements View.OnClickListener {
    Context context;
    String address;

    TextView label;
    LinearLayout horizontalLayout;
    Button addButton;

    public FoundLockView(Context context, String name, String address) {
        this.context = context;
        this.address = address;
        float dpi = context.getResources().getDisplayMetrics().density;
        horizontalLayout = new LinearLayout(context);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(100*dpi)));

        label = new TextView(context);
        label.setText(name);

        addButton = new Button(context);
        addButton.setText("Add");
        addButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        addButton.setOnClickListener(this);

        horizontalLayout.addView(label);
        horizontalLayout.addView(addButton);
    }

    public LinearLayout getLayout() {
        return horizontalLayout;
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Device password");


        final EditText input = new EditText(context);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = input.getText().toString();
                String name = label.getText().toString();

                SharedPreferences sharedpreferences = context.getSharedPreferences("AddressToPassword", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(address, password);
                editor.commit();

                sharedpreferences = context.getSharedPreferences("AddressToName", Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.putString(address, name);
                editor.commit();

                sharedpreferences = context.getSharedPreferences("NameToAddress", Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.putString(name, address);
                editor.commit();

                sharedpreferences = context.getSharedPreferences("AddressAutolog", Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.putBoolean(address, false);
                editor.commit();

                // TODO CONNECT TO BTH
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
}
