package com.example.jauzfaktneviem;


import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;
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
    private ImageView bellLines;
    private Button treeButton;
    private boolean bellState;
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
        ImageButton DoorButton = findViewById(R.id.door);

        receivedTmpIn = findViewById(R.id.tmpIn);
        receivedTmpOut = findViewById(R.id.tmpOut);
        receivedHmdOut = findViewById(R.id.hmdOut);
        bellLines = findViewById(R.id.bellLines);
        treeButton = findViewById(R.id.treeSong);
        progressBarIn = findViewById(R.id.temperatureInProgressBar);
        progressBarOut = findViewById(R.id.temperatureOutProgressBar);


        final boolean[] isButtonPressed = {false};
        LightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenuVisibility(lightMenu);
                if (isButtonPressed[0]) {
                    LightMenu.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    LightMenu.setImageResource(R.drawable.lightbulb_img);
                    isButtonPressed[0] = Boolean.FALSE;

                } else {
                    LightMenu.setBackground(getResources().getDrawable(R.drawable.roundbutton_off));
                    LightMenu.setImageResource(R.drawable.lightbulb_img_on);
                    isButtonPressed[0] = Boolean.TRUE;
                }



            }
        });
        WindowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenuVisibility(windowMenu);
                if (isButtonPressed[0]) {
                    WindowMenu.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    WindowMenu.setImageResource(R.drawable.window_img);
                    isButtonPressed[0] = Boolean.FALSE;

                } else {
                    WindowMenu.setBackground(getResources().getDrawable(R.drawable.roundbutton_off));
                    WindowMenu.setImageResource(R.drawable.window_img_on);
                    isButtonPressed[0] = Boolean.TRUE;
                }



            }
        });
        DoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isButtonPressed[0]) {
                    DoorButton.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    DoorButton.setImageResource(R.drawable.door_img);
                    isButtonPressed[0] = Boolean.FALSE;

                    try {
                        outputStream.write('C');
                        Log.d("Data Sent", "C");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    DoorButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_off));
                    DoorButton.setImageResource(R.drawable.door_img_on);
                    isButtonPressed[0] = Boolean.TRUE;

                    try {
                        outputStream.write('c');
                        Log.d("Data Sent", "c");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        treeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputStream.write('f');
                    Log.d("Data Sent", "f");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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
                        Log.d("Data Sent", "d");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('D');
                        Log.d("Data Sent", "D");
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
                        Log.d("Data Sent", "e");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('E');
                        Log.d("Data Sent", "E");
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
                        outputStream.write('a');
                        Log.d("Data Sent", "a");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('A');
                        Log.d("Data Sent", "A");
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
                        outputStream.write('b');
                        Log.d("Data Sent", "b");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('B');
                        Log.d("Data Sent", "B");
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
                        outputStream.write('b');
                        Log.d("Data Sent", "b");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        outputStream.write('B');
                        Log.d("Data Sent", "B");
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
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation != Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
                if (receivedMessage.length() == 19 && hasCiarka(receivedMessage) == 3) {
                    String[] dividedStrings = receivedMessage.split(",");
                    receivedTmpIn.setText(dividedStrings[0].substring(0, 4));
                    receivedHmdOut.setText(dividedStrings[2].substring(0, 2));
                    receivedTmpOut.setText(dividedStrings[1].substring(0, 4));
                    progressBarIn.setProgress(Integer.parseInt(dividedStrings[0].substring(0, 2)));
                    progressBarOut.setProgress(Integer.parseInt(dividedStrings[1].substring(0, 2)));

                    if (dividedStrings[3].equals("1")){
                        bellState = true;
                        showAlert("INTRUDEEEEEER");
                    }else{
                        bellState = false;
                    }
                    ringBell();

                }
            }
        }
    }

    private void ringBell(){
        if (bellState) {
            bellLines.setVisibility(View.VISIBLE);

            try {
                Thread.sleep(3000); // 2000 milliseconds (2 seconds) delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{

            bellLines.setVisibility(View.GONE);
        }

    }
    private int hasCiarka(String sentence){
        int count = 0;
        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) == ',') {
                count++;
            }
        }
        return count;
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setTitle("Alert"); // Set the title of the dialog
        AlertDialog alertDialog = builder.setMessage(message).show();

        // Get the TextView responsible for the message and set its text color
        TextView messageTextView = alertDialog.findViewById(android.R.id.message);
        if (messageTextView != null) {
            messageTextView.setTextColor(getResources().getColor(R.color.white));
        }

        // Set a click listener for the "OK" button to dismiss the dialog
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }




}

