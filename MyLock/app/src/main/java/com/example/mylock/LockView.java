package com.example.mylock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class LockView implements  LockViewUpdate, View.OnClickListener {
    public static final int STATE_LOCKED = 0;
    public static final int STATE_UNLOCKED = 1;
    public static final int STATE_UNAVAILABLE = 2;

    LinearLayout layout;

    TextView title;
    ImageView icon;
    Button settings;
    Button lockunlock;

    Context context;

    public LockView(Context context, String name, int state) {
        this.context = context;

        float dpi = context.getResources().getDisplayMetrics().density;
        float width = context.getResources().getDisplayMetrics().widthPixels;
        float height = context.getResources().getDisplayMetrics().heightPixels;

        LinearLayout verticalLayout = new LinearLayout(context);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
//        verticalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        verticalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        verticalLayout.setPadding(25, 25, 25,25);

        title = new TextView(context);
        title.setText(name);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(10*dpi);
        title.setTextColor(Color.YELLOW);

        LinearLayout horizontalLayout = new LinearLayout(context);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(100*dpi)));

        icon = new ImageView(context);
        settings = new Button(context);
        settings.setText("Open settings");
        settings.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        settings.setGravity(Gravity.CENTER);
        settings.setOnClickListener(this);
        lockunlock = new Button(context);
        lockunlock.setGravity(Gravity.CENTER);
        lockunlock.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lockunlock.setOnClickListener(this);

        horizontalLayout.addView(icon);
        horizontalLayout.addView(settings);
        horizontalLayout.addView(lockunlock);

        onStateChanged(state);
        verticalLayout.addView(title);
        verticalLayout.addView(horizontalLayout);
        layout = verticalLayout;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    @Override
    public void onStateChanged(int state) {
        int resId = R.drawable.bkg_gray;
        String text = "Lock/unlock";

        if (state == STATE_LOCKED) {
            resId = R.drawable.bkg_red;
            text = "Unlock";
        }
        else if (state == STATE_UNLOCKED) {
            resId = R.drawable.bkg_green;
            text = "Lock";
        }
        else if (state == STATE_UNAVAILABLE) {}

        icon.setBackgroundResource(resId);
        lockunlock.setText(text);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        if (button.getText().toString().equals("Open settings")) {
            // Start new activity
            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, pag_yala.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", this.title.getText().toString());
            intent.putExtras(bundle);
            context.startActivity(intent);

        }
        else {
            // Some bth
        }
    }

    public String getName() {
        return title.getText().toString();
    }
}
