package com.behadd.shareimages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.hasExtra("secretMessage")) {

            String secret = intent.getStringExtra("secretMessage").toString();
            Toast.makeText(context,"Hello from ShareImages! "+secret, Toast.LENGTH_SHORT).show();

        }
    }
}
