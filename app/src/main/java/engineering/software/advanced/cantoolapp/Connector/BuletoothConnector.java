package engineering.software.advanced.cantoolapp.Connector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ningge on 2017/10/10.
 *
 * connect device by bluetooth
 */

public class BuletoothConnector implements Connector {
    private  static BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice device = null;
    private BluetoothSocket socket = null;
    private static String TAG = "BluetoothConnector";
    //UUID for devices except Android devices
    private static UUID OTHER_DEVICE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public void connect(String path, int rate) {
        device = adapter.getRemoteDevice(path);

        // clean old connection first
        close();

        try {
            socket = device.createRfcommSocketToServiceRecord(OTHER_DEVICE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("log", "获取Socket失败");
            return;
        }
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            socket.connect();
            Log.d(TAG, "connect to bluetooth device success");
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            connectException.printStackTrace();
            Log.d(TAG, "connect to bluetooth device failed");
            try {
                socket.close();
            } catch (IOException closeException) { }
            return;
        }
    }

    @Override
    public Map listAll() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        try {
            return socket == null ? null : socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "get input stream failed");
            return null;
        }
    }

    @Override
    public OutputStream getOutputStream() {
        try {

            return socket == null ? null : socket.getOutputStream();
        } catch ( IOException e) {
            e.printStackTrace();
            Log.e(TAG, "get output stream failed");
            return null;
        }
    }

    @Override
    public void close() {
        if (device != null) {
            device = null;
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "close socket failed");
            }
        }
    }
}
