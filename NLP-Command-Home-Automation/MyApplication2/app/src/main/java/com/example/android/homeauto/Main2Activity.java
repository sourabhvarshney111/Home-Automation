package com.example.android.homeauto;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Main2Activity extends AppCompatActivity {
        Button b1, b2,b4, b5;
        BluetoothAdapter BA;
        String address = null;
        BluetoothSocket btSocket = null;
        Set<BluetoothDevice> pairedDevices;
        static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        TextView ta;
        ListView lv;
        MainActivity obj=new MainActivity();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            lv = (ListView) findViewById(R.id.lv);
            b1 = (Button) findViewById(R.id.button);
            b2 = (Button) findViewById(R.id.button2);
            b4 = (Button) findViewById(R.id.button4);
            b5 = (Button) findViewById(R.id.button5);
            BA = BluetoothAdapter.getDefaultAdapter();

        }

        @SuppressLint("ClickableViewAccessibility")
        private void setw() throws IOException {
            bluetooth_connect_device();
        }

        private void bluetooth_connect_device() throws IOException {
            try {
                BA = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = BA.getBondedDevices();
                final ArrayList list = new ArrayList();
                Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
                for (BluetoothDevice bt : pairedDevices) {
                    list.add(bt.getName());
                    if (bt.getName().toString().equals("H-C-2010-06-01")) {
                        address = bt.getAddress();
                        Toast.makeText(getApplicationContext(), bt.getName().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
                lv.setAdapter(adapter);
            } catch (Exception we) {
            }
            BA = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
            BluetoothDevice dispositivo = BA.getRemoteDevice(address);//connects to the device's address and checks if it's available
            btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
            btSocket.connect();

        }

        public void on(View v) {
            if (!BA.isEnabled()) {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 0);
                Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
            }
        }
        public void off(View v) {
            BA.disable();
            Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
        }

        public void visible(View v) {
            Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(getVisible, 0);
            try {
                setw();
            } catch (Exception e) {
            }
        }

        public void led_on(View v) {
            try {
                String i = obj.order;
                if (btSocket != null) {
                    Toast.makeText(getApplicationContext(),i, Toast.LENGTH_SHORT).show();
                    btSocket.getOutputStream().write(i.toString().getBytes());
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "couldn't send", Toast.LENGTH_SHORT).show();

            }

        }


}

