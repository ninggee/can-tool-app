package engineering.software.advanced.cantoolapp.webinterfaces;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import engineering.software.advanced.cantoolapp.communicator.Reader;
import engineering.software.advanced.cantoolapp.communicator.ReaderThread;
import engineering.software.advanced.cantoolapp.communicator.handler.Handler;
import engineering.software.advanced.cantoolapp.connector.Connector;
import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.Processor;
import engineering.software.advanced.cantoolapp.converter.entity.Message;

/**
 * Created by ningge on 20/10/2017.
 */

public class TestInterface {

    Context __context;
    Connector __connector;
    ArrayList<MessagesWrapper> messages = new ArrayList();
    long start_time = new Date().getTime();
    ReaderThread reader = null;

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

        reader = new ReaderThread(in, new Handler() {
            @Override
            public void handle(String message) {
                long recieve_time = new Date().getTime();
                Log.d("message", message);
                Set<Message> set = processor.decodeMultiple(message);
                synchronized (messages){

                    for(Message can_message: set) {
                        messages.add(new MessagesWrapper(recieve_time - start_time + "", can_message));
                    }
                }
//                messages.put(recieve_time - start_time , processor.decodeMultiple(message));

            }
        });

        reader.start();

        return true;
    }


    @JavascriptInterface
    public boolean stopRead() {
        try {
            reader.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("thread", "interrupt error");
            return false;
        }
        return  true;
    }

    @JavascriptInterface
    public String getMessages() {
        Gson gson = new Gson();
        synchronized (this.messages) {
            Log.d("gsontest", gson.toJson(this.messages));
            return gson.toJson(this.messages);
        }
    }
}
