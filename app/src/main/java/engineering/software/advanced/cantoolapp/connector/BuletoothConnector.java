package engineering.software.advanced.cantoolapp.connector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ningge on 2017/10/10.
 *
 * connect device by bluetooth
 */

public class BuletoothConnector implements Connector {
    private  BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice device = null;
    private BluetoothSocket socket = null;
    private static String TAG = "BluetoothConnector";
    //UUID for devices except Android devices
    private static UUID OTHER_DEVICE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public boolean connect(String path, int rate) {
        // clean old connection first
        close();

        device = adapter.getRemoteDevice(path);
        boolean state = true;

        try {
            socket = device.createRfcommSocketToServiceRecord(OTHER_DEVICE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "获取Socket失败");
            state = false;
            return state;
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
            state  = false;
            try {
                socket.close();
            } catch (IOException closeException) { }
        }

        return state;
    }

    @Override
    public Map listAll() {
        Map devices = new HashMap();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                devices.put(device.getName(), device.getAddress());
                Log.i(TAG,device.getName() + "\n" + device.getAddress());
            }
        }
        return devices;
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
    public boolean close() {

        boolean state = true;
        if (device != null) {
            device = null;
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                state = false;
                Log.e(TAG, "close socket failed");
            }
        }

        return state;
    }


}
