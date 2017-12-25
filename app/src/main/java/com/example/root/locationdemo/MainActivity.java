package com.example.root.locationdemo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String uniqueCode;


    public void onClickSave (View view )
    {
        EditText editText= (EditText) findViewById(R.id.editText);

        Editable uniqueCode= editText.getText();
        Log.i("clicked save", String.valueOf(uniqueCode));


        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("MYLABEL", String.valueOf(uniqueCode)).apply();
        TextView editText2= (TextView) findViewById(R.id.editText2);
        editText2.setText(uniqueCode);


        editText.setVisibility(8);
        Button button = (Button) findViewById(R.id.button);
        button.setVisibility(8);
        Button button2= (Button) findViewById(R.id.button2);
        button2.setVisibility(0);
        TextView textView = (TextView) findViewById(R.id.editText2);
        textView.setVisibility(0);

    }

    public void onClickEdit (View view )
    {
        EditText editText= (EditText) findViewById(R.id.editText);
        int a=0;
        editText.setVisibility(a);
        Button button = (Button) findViewById(R.id.button);
        button.setVisibility(0);
        Button button2= (Button) findViewById(R.id.button2);
        button2.setVisibility(8);
        TextView textView = (TextView) findViewById(R.id.editText2);
        textView.setVisibility(8);
    }

    public void onClickShare (View view )
    {
        TextView textView = (TextView) findViewById(R.id.editText2);
        uniqueCode= String.valueOf(textView.getText());
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("MYLABEL", String.valueOf(uniqueCode)).apply();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, uniqueCode);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uniqueCode=PreferenceManager.getDefaultSharedPreferences(this).getString("MYLABEL", "defaultStringIfNothingFound");

        TextView editText2= (TextView) findViewById(R.id.editText2);
        editText2.setText(uniqueCode);









        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);

        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);

        }






    }

}
