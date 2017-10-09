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

        if (!bt.isBluetoothAvailable()) {
            Log.i("debug", "bluetooth is not available");
        }

        if (bt.isBluetoothEnabled()) {
            Log.i("Debug", "bluetooth is enabled");
            Intent intent = new Intent(getApplicationContext(), DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);


        } else {
            Log.i("Debug", "bluetooth is not enabled");
        }


        setContentView(R.layout.activity_main);
        mEditTextEmission = (EditText) findViewById(R.id.editTextEmission);


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
                Toast.makeText(mApplication, "收到消息：" + new String(buffer) + "  size = " + size, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
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
