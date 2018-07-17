package com.example.davidb.bridgestone_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;


public class MainActivity extends AppCompatActivity {

    public String udpOutputData;



    //Battery Level Image display
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
            else if (level < 5)
                batteryLevel.setImageResource(batteryArray[4]);
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        battery_text = findViewById(R.id.textView3);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
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
                } catch (InterruptedException e) {
                }

            }
        };
        t.start();


    }

}

