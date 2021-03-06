package engineering.software.advanced.cantoolapp.connector;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import engineering.software.advanced.cantoolapp.libs.SerialPort;
import engineering.software.advanced.cantoolapp.libs.SerialPortFinder;

/**
 * Created by ningge on 2017/10/10.
 *
 * connect device by serial port
 *
 * warning: this class can only support Android API Level below 23(Android 6.0)
 */

public class SerialPortConnector implements Connector {
    private SerialPort serialPort = null;
    private SerialPortFinder serialPortFinder = new SerialPortFinder();
    private static String TAG = "SerialPortConnector";

    @Override
    public boolean connect(String path, int rate) {

        boolean state = true;
        if (serialPort != null) {
            close();
        }

        try {
            serialPort = new SerialPort(new File(path), rate, 0);
        } catch (IOException e) {
            e.printStackTrace();
            state = false;
            Log.e(TAG, "can't connect to that port");
        }

        return state;

    }

    @Override
    public Map listAll() {
        Map ports = new HashMap<>();
        String[] devices = serialPortFinder.getAllDevices();
        String[] path = serialPortFinder.getAllDevicesPath();

        for (int i = 0; i < devices.length; i++) {
            ports.put(devices[i], path[i]);
        }

        return ports;
    }

    @Override
    public InputStream getInputStream() {
        return serialPort == null ? null: serialPort.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return serialPort == null ? null : serialPort.getOutputStream();
    }

    @Override
    public boolean close() {
        if (serialPort == null) return false;
        serialPort.close();
        serialPort = null;
        return  true;
    }
}
