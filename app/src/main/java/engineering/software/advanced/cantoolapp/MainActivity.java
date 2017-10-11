package engineering.software.advanced.cantoolapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends SerialPortActivity {

    private EditText mEditTextEmission;
    private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        bt = new BluetoothSPP(context);

        // check if device has bluetooth
        if (!bt.isBluetoothAvailable()) {
            Log.i("debug", "bluetooth is not available");

        } else {
            // device has bluetooth

            // check if device open bluetooth
            if (bt.isBluetoothEnabled()) {
                Log.i("Debug", "bluetooth is enabled");

                bt.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {
                    public void onServiceStateChanged(int state) {
                        if(state == BluetoothState.STATE_CONNECTED)
                            Log.i("state", "collected");
                                    // Do something when successfully connected
                        else if(state == BluetoothState.STATE_CONNECTING)
                                Log.i("state", "collecting");        // Do something while connecting
                        else if(state == BluetoothState.STATE_LISTEN)
                                Log.i("state", "waiting for connection");
                                        // Do something when device is waiting for connection
                        else if(state == BluetoothState.STATE_NONE)
                                Log.i("state", "none");
                                    // Do something when device don't have any connection
                    }
                });
                bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
                    public void onDeviceConnected(String name, String address) {
                        // Do something when successfully connected
                        Log.i("state", "connected");
                    }

                    public void onDeviceDisconnected() {
                        Log.i("state", "disconnected");// Do something when connection was disconnected
                    }

                    public void onDeviceConnectionFailed() {
                        // Do something when connection failed
                        Log.i("state", "connection failed");
                    }
                });

                Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);

            } else {
                Log.i("Debug", "bluetooth is not enabled");
            }

        }

//        connector example code
//        Connector connector = new SerialPortConnector();
//
//        Map ports = connector.listPort();
//
//        Iterator port = ports.entrySet().iterator();
//
//        while (port.hasNext()) {
//            Map.Entry entry = (Map.Entry) port.next();
//
//            Log.i("port info", (String)entry.getKey() + " : " + (String)entry.getValue());
//        }
//
//        setContentView(R.layout.activity_main);
//        mEditTextEmission = (EditText) findViewById(R.id.editTextEmission);


    }

    public void send(View view) {
        String text = mEditTextEmission.getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Message message = Message.obtain();
        message.obj = text.getBytes();
        sendingHandler.sendMessage(message);
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mEditTextEmission != null) {
                    mEditTextEmission.append(new String(buffer, 0 , size));
                }
                Toast.makeText(mApplication, "收到消息：" + new String(buffer) + "  size = " + size, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                testConnect(data.getStringExtra(BluetoothState.EXTRA_DEVICE_ADDRESS));
//                Log.i("data", data.getStringExtra(BluetoothState.EXTRA_DEVICE_ADDRESS));
//                bt.connect(data.getStringExtra(BluetoothState.EXTRA_DEVICE_ADDRESS));
//                Log.i("bluetooth state 1", bt.getServiceState() + "");
//                bt.send("你好", true);
//                Log.i("bluetooth state 2", bt.getServiceState() + "");
//                Log.i("bluetooth", bt.getConnectedDeviceName());
            }
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
            } else {
                // Do something if user doesn't choose any device (Pressed back)
            }
        }
    }

    private void  testConnect(String address) {
        BluetoothSocket socket = null;
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = adapter.getRemoteDevice(address);
        try {
            // 蓝牙串口服务对应的UUID。如使用的是其它蓝牙服务，需更改下面的字符串
            UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("log", "获取Socket失败");
            return;
        }
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            socket.connect();
            Log.d("log", "连接成功");
            InputStream mmInStream = socket.getInputStream();
            OutputStream mmOutStream = socket.getOutputStream();
            byte[] bytes = "test connect".getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }

            //接收数据
            byte[] buffer = new byte[1024];
            try {
                int state = mmInStream.read(buffer);
                Log.i("length", state + "");
                Log.i("read message", new String(buffer, 0, state));
                Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG);
            } catch (IOException e) {

            }

        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            connectException.printStackTrace();
            Log.d("log", "连接失败");
            try {
                socket.close();
            } catch (IOException closeException) { }
            return;
        }


    }

}
