package com.example.mylock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mylock.LockView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Lista sa nu moara lockviewurile create
    List<LockView> lockLayouts = new ArrayList<LockView>();

    public void displaySavedLocks() {
        SharedPreferences sharedpreferences = getSharedPreferences("Locks", Context.MODE_PRIVATE);
        Map<String, ?> stored = sharedpreferences.getAll();

        LinearLayout layout = this.findViewById(R.id.lockslayout);
        for (Map.Entry<String, ?> entry : stored.entrySet()) {
            String name = entry.getKey();
            String password = (String) entry.getValue();
            LockView lock = new LockView(this, name, LockView.STATE_UNAVAILABLE);
            layout.addView(lock.getLayout());
            lockLayouts.add(lock);
        }
    }

    public void removeLockByName(String name) {
        LinearLayout layout = this.findViewById(R.id.lockslayout);

        LockView toDelete = null;
        for(LockView lock: lockLayouts) {
            if (lock.getName().equals(name)) toDelete = lock;
        }

        lockLayouts.remove(toDelete);
        layout.removeView(toDelete.getLayout());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Test
        SharedPreferences sharedpreferences = getSharedPreferences("Locks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("test", "test");
        editor.commit();

        displaySavedLocks();
    }

    // Onclick pt butonul de adaugare
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, cautare_yala.class);
        startActivity(intent);
    }
}
