package com.example.makepinnedshortcut;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button[] buttons = new Button[2];
    int[] BUTTONIDS = {R.id.button1, R.id.button2};
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        for (int i = 0; i < 2; i++) {
            buttons[i] = findViewById(BUTTONIDS[i]);
            buttons[i].setOnClickListener(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                makeShortcut(mContext, "test1");
                break;
            case R.id.button2:
                makeShortcut(mContext, "test2");
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeShortcut(Context context, String string) {
        ShortcutManager shortcutManager =
                context.getSystemService(ShortcutManager.class);

        if (shortcutManager.isRequestPinShortcutSupported()) {

            ShortcutInfo pinShortcutInfo =
                    new ShortcutInfo.Builder(context, string).build();

            Intent pinnedShortcutCallbackIntent =
                    shortcutManager.createShortcutResultIntent(pinShortcutInfo);

            PendingIntent successCallback = PendingIntent.getBroadcast(context,
                    /* request code */ 0,
                    pinnedShortcutCallbackIntent,
                    /* flags */ 0);

            shortcutManager.requestPinShortcut(pinShortcutInfo,
                    successCallback.getIntentSender());
        }
    }

}