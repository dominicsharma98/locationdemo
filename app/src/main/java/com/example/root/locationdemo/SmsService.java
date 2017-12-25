package com.example.root.locationdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import static android.content.ContentValues.TAG;


public class SmsService extends Service {


    String sendernum;

    private String getContactName(Context context, String number) {

        String name = null;

        String[] projection = new String[]{
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.PhoneLookup._ID};


        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));


        Cursor cursor = context.getContentResolver().query(contactUri, projection, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                Log.v(TAG, "Started uploadcontactphoto: Contact Found @ " + number);
                Log.v(TAG, "Started uploadcontactphoto: Contact name  = " + name);
            } else {
                Log.v(TAG, "Contact Not Found @ " + number);
                return sendernum;
            }
            cursor.close();
        }
        return name;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);


        if (intent.getStringExtra("data") != null) {

            sendernum = intent.getStringExtra("data");


        }


        Log.i("ikjijojjjjjjjjjjjjjjjj", "                 onstart");
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        final LocationListener locationListener = new LocationListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onLocationChanged(Location location) {


                Log.i("Location", "ghjhgfdghgfghjhgfghjhgfghgfgh");
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                final SmsManager sms = SmsManager.getDefault();


                try {

                    if (location.getAccuracy() < 50) {
                        sms.sendTextMessage(sendernum, null, "https://www.google.com/maps/?q=" + lat + "," + lon, null, null);
                        locationManager.removeUpdates(this);


                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SmsService.this);

                        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                        mBuilder.setContentTitle("Location Shared with");

                        String user = getContactName(SmsService.this, sendernum);

                        mBuilder.setContentText(user);
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                        mNotificationManager.notify(1, mBuilder.build());


                    }


                } catch (Exception e) {
                    Log.e(TAG, "Exception smsReceiver: ");
                }


            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d(TAG, "onStatusChanged: " + s);

            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d(TAG, "onProviderEnabled: " + s);

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };




            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);




    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate service ");


    }

}

