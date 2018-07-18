package com.example.davidb.bridgestone_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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




    //Battery Level Image display
    private TextView battery_text;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override

        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery_text.setText(String.valueOf(level));
            battery_text.setVisibility(View.INVISIBLE);

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
            else if (level <= 5)
                batteryLevel.setImageResource(batteryArray[4]);
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextPress = findViewById(R.id.editTextPress);
        final EditText editTextTyre = findViewById(R.id.editTyre);

        editTextPress.requestFocus();

        editTextPress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
if (editTextPress.getText().toString().length()==5){
    editTextTyre.requestFocus();
}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Button verify = (Button) findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            //onClick method for Verify button
            public void onClick(View arg0) {

                Thread thread = new Thread(){
                    public void run(){
                        String pressText;
                        String tyreText;
                        pressText = editTextPress.getText().toString();
                        tyreText = editTextTyre.getText().toString();

                        String msg = (pressText + "," + tyreText);



                        int port = 1521;
                        try {
                            DatagramSocket s = new DatagramSocket();
                            InetAddress local = InetAddress.getByName("10.3.22.206");
                            int msg_length = msg.length();
                            byte[] message = msg.getBytes();
                            DatagramPacket p = new DatagramPacket(message, msg_length, local, port);
                            s.send(p);
                        }catch (SocketException e) {
                            e.printStackTrace();
                        }catch (UnknownHostException e) {
                            e.printStackTrace();
                        }catch(IOException e) {
                            e.printStackTrace();
                        }

                    }


                };
                thread.start();

                editTextPress.requestFocus();
                if (editTextPress.hasFocus() == true){
                    editTextPress.setText("");
                    editTextTyre.setText("");
                }




            }


        });




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

