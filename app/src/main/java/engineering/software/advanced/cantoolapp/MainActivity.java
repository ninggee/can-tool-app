package engineering.software.advanced.cantoolapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import engineering.software.advanced.cantoolapp.Connector.Connector;
import engineering.software.advanced.cantoolapp.Connector.SerialPortConnector;

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
                Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);

            } else {
                Log.i("Debug", "bluetooth is not enabled");
            }

        }


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
                bt.connect(data);
                bt.send("你好", true);
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

}
