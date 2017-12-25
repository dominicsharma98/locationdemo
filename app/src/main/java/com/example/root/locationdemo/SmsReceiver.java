package com.example.root.locationdemo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Objects;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "IncomingSms";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {




        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    Log.i(TAG, "                 p1");

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    Log.i(TAG, "                 p2");
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    Log.i(TAG, "                 p3");

                    String senderNum = phoneNumber;
                    Log.i(TAG, "                 p4");
                    String text = currentMessage.getDisplayMessageBody();
                    Log.i(TAG, "                 p5");

                    Log.i(TAG, "senderNum: " + senderNum + "; message: " + text);
                    String uniqueCode=PreferenceManager.getDefaultSharedPreferences(context).getString("MYLABEL", "defaultStringIfNothingFound");
                    Log.i("unique code iss",uniqueCode);

                    if (Objects.equals(text, uniqueCode)) {

                        Log.i(TAG, "                 p6");
                        Intent intent1 = new Intent(context, SmsService.class);
                        String name = "data";
                        intent1.putExtra(name,senderNum);
                        context.startService(intent1);
                        context.stopService(intent1);
                    }


                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception smsReceiver: " + e.toString());
        }


    }


}
