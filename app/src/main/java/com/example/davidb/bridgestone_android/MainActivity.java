package com.example.davidb.bridgestone_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            battery_text.setText(String.valueOf(level) + "%");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        final int[] batteryArray = {
                R.drawable.slide1,
                R.drawable.slide2,
                R.drawable.slide3,
                R.drawable.slide4,
                R.drawable.slide5
        };

        battery_text = findViewById(R.id.textView3);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        if (battery_text = 100%){
            ImageView batteryLevel = findViewById(R.id.imageView2);
            batteryLevel.setImageResource(batteryArray[0]);
        }


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

