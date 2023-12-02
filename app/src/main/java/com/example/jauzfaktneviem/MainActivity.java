package com.example.jauzfaktneviem;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.widget.CompoundButton;
public class MainActivity extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private TextView receivedTmpIn;
    private TextView receivedTmpOut;
    private TextView receivedHmdOut;
    private Switch SwitchRight;
    private Switch SwitchLeft;
    private Switch SwitchOut;
    private Switch SwitchKitchen;
    private Switch SwitchBedroom;

    private ProgressBar progressBarIn;
    private ProgressBar progressBarOut;
    private static final UUID HC05_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String HC05_ADDRESS = "00:02:5B:00:A7:12";

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // Handle messages here if needed
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        LinearLayout lightMenu = findViewById(R.id.lightMenu);
        LinearLayout windowMenu = findViewById(R.id.windowMenu);
        ImageButton LightMenu = findViewById(R.id.lightbulb);
        ImageButton WindowMenu = findViewById(R.id.window);
        receivedTmpIn = findViewById(R.id.tmpIn);
        receivedTmpOut = findViewById(R.id.tmpOut);
        receivedHmdOut = findViewById(R.id.hmdOut);


        progressBarIn = findViewById(R.id.temperatureInProgressBar);
        progressBarOut = findViewById(R.id.temperatureOutProgressBar);



        LightMenu.setOnClickListener(view -> toggleMenuVisibility(lightMenu));
        WindowMenu.setOnClickListener(view -> toggleMenuVisibility(windowMenu));


        Switch switchLeft = findViewById(R.id.switchLeft);
        Switch switchRight = findViewById(R.id.switchRight);
        Switch switchOut = findViewById(R.id.switchOut);
        Switch switchKitchen = findViewById(R.id.switchKitchen);
        Switch switchBedroom = findViewById(R.id.switchBedroom);

        switchLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Perform actions when SwitchLeft changes to checked state
                    try {
                        outputStream.write('d');
                        Log.d("Data Sent", "c");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('D');
                        Log.d("Data Sent", "C");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        switchRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Perform actions when SwitchLeft changes to checked state
                    try {
                        outputStream.write('e');
                        Log.d("Data Sent", "E");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('C');
                        Log.d("Data Sent", "C");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        switchOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Perform actions when SwitchLeft changes to checked state
                    try {
                        outputStream.write('c');
                        Log.d("Data Sent", "c");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('C');
                        Log.d("Data Sent", "C");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        switchKitchen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Perform actions when SwitchLeft changes to checked state
                    try {
                        outputStream.write('c');
                        Log.d("Data Sent", "c");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('C');
                        Log.d("Data Sent", "C");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        switchBedroom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Perform actions when SwitchLeft changes to checked state
                    try {
                        outputStream.write('c');
                        Log.d("Data Sent", "c");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('C');
                        Log.d("Data Sent", "C");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }

        if (!bluetoothAdapter.isEnabled()) {
            // Request user to enable Bluetooth
            // ...
        }

        ConnectBT connectBT = new ConnectBT();
        connectBT.execute();
    }

    private void toggleMenuVisibility(LinearLayout menu) {
        boolean isVisible = menu.getVisibility() == View.VISIBLE;
        menu.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    private class ConnectBT extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (socket == null || !socket.isConnected()) {
                    BluetoothDevice hc05 = bluetoothAdapter.getRemoteDevice(HC05_ADDRESS);
                    socket = hc05.createInsecureRfcommSocketToServiceRecord(HC05_UUID);
                    socket.connect();
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();
                    startListening();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void startListening() {
            StringBuilder messageBuffer = new StringBuilder();

            while (true) {
                try {
                    int receivedByte = inputStream.read();

                    if (receivedByte == -1) {
                        break;
                    }

                    char character = (char) receivedByte;

                    if (character == '\n') {
                        String receivedMessage = messageBuffer.toString();
                        publishProgress(receivedMessage);
                        messageBuffer.setLength(0);
                    } else {
                        messageBuffer.append(character);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... receivedMessages) {
            super.onProgressUpdate(receivedMessages);
            if (receivedMessages != null && receivedMessages.length > 0) {
                String receivedMessage = receivedMessages[0];

                // Process received message or update UI accordingly
                Log.d("ReceivedMessage", receivedMessage);
                if (receivedMessage.length() == 17) {
                    String[] dividedStrings = receivedMessage.split(",");
                    receivedTmpIn.setText(dividedStrings[0].substring(0, 4));
                    receivedHmdOut.setText(dividedStrings[2].substring(0, 2));
                    receivedTmpOut.setText(dividedStrings[1].substring(0, 4));
                    progressBarIn.setProgress(Integer.parseInt(dividedStrings[0].substring(0, 2)));
                    progressBarOut.setProgress(Integer.parseInt(dividedStrings[1].substring(0, 2)));
                }
            }
        }
    }
}

