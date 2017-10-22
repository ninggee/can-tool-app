package engineering.software.advanced.cantoolapp.webinterfaces;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import engineering.software.advanced.cantoolapp.communicator.ReaderThread;
import engineering.software.advanced.cantoolapp.communicator.handler.Handler;
import engineering.software.advanced.cantoolapp.connector.Connector;
import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.Processor;

/**
 * Created by ningge on 20/10/2017.
 */

public class TestInterface {

    Context __context;
    Connector __connector;
    Map messages = new HashMap();
    long start_time = new Date().getTime();

    Processor processor = new MessageAndSignalProcessor();


    public TestInterface(Context c, Connector connector) {
        __context = c;
        __connector =connector;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(__context, toast, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public String getDevices() {
        JSONObject result = new JSONObject(__connector.listAll());
        Log.d("interface", result.toString());
        return  result.toString();
    }

    @JavascriptInterface
    public boolean connect(String path) {
        return  __connector.connect(path, 115200);
    }

    @JavascriptInterface
    public boolean close() {
        return __connector.close();
    }

    @JavascriptInterface
    public boolean startRead() {
        InputStream in = __connector.getInputStream();

        ReaderThread readerThread = new ReaderThread(in, new Handler() {
            @Override
            public void handle(String message) {
                long recieve_time = new Date().getTime();
                messages.put(recieve_time, processor.decode(message));
            }
        });

        readerThread.start();

        return true;
    }
}
