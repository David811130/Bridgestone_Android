package com.example.davidb.bridgestone_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Inetaddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.view.View;



public class MainActivity extends AppCompatActivity {

    private TextView battery_text;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery_text.setText(String.valueOf(level));

            final int[] batteryArray = {
                    R.drawable.slide1,
                    R.drawable.slide2,
                    R.drawable.slide3,
                    R.drawable.slide4,
                    R.drawable.slide5
            };
            ImageView batteryLevel = findViewById(R.id.imageView2);
            if (level > 75)
                batteryLevel.setImageResource(batteryArray[0]);
            else if (level > 50)
                batteryLevel.setImageResource(batteryArray[1]);
            else if (level > 25)
                batteryLevel.setImageResource(batteryArray[2]);
            else if (level > 15)
                batteryLevel.setImageResource(batteryArray[3]);
            else if (level > 5)
                batteryLevel.setImageResource(batteryArray[4]);
            }



    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editTextPress = findViewById(R.id.editText);
        EditText editTextTyre = findViewById(R.id.editText3);
        editTextPress.requestFocus();
        if (editTextPress.getText().length() > 7) {
            editTextPress.clearFocus();
            editTextTyre.requestFocus();
        }






        battery_text = findViewById(R.id.textView3);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));





        Thread t = new Thread() {
            @Override
            public void run(){
                try{
                    while(!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (TextView) findViewById(R.id.date);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy\nhh:mm:ss a");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);

                            }
                        });
                    }
                } catch (InterruptedException e){
            }

                }
            };
        t.start();
        }


    }

