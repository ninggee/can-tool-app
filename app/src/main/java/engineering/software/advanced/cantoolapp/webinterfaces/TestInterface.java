package engineering.software.advanced.cantoolapp.webinterfaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engineering.software.advanced.cantoolapp.communicator.Reader;
import engineering.software.advanced.cantoolapp.communicator.ReaderThread;
import engineering.software.advanced.cantoolapp.communicator.handler.Handler;
import engineering.software.advanced.cantoolapp.connector.Connector;
import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.Processor;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;
import engineering.software.advanced.cantoolapp.converter.entity.Message;
import engineering.software.advanced.cantoolapp.converter.transmission.Impl.SenderImpl;
import engineering.software.advanced.cantoolapp.converter.transmission.Sender;

/**
 * Created by ningge on 20/10/2017.
 */

public class TestInterface {

    Context __context;
    Connector __connector;
    SharedPreferences can_setting;
    CommandController commandController;
    //store all messages received
    ArrayList<MessagesWrapper> messages = null;
    //store only most recently message for one id
    Map<String, MessagesWrapper> uniqueMessages = null;

    long start_time = new Date().getTime();
    ReaderThread reader = null;

    Processor processor = new MessageAndSignalProcessor();


    public TestInterface(Context c, Connector connector, SharedPreferences sharedPreferences) {
        __context = c;
        __connector =connector;
        can_setting = sharedPreferences;
        commandController = new CommandController(__connector);
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
        messages = new ArrayList<>();
        uniqueMessages = new HashMap<>();

        reader = new ReaderThread(in, new Handler() {
            @Override
            public void handle(String message) {
                if(messages == null) {
                    return;
                }

                long recieve_time = new Date().getTime();
                Set<Message> set = processor.decodeMultiple(message);
                synchronized (messages){

                    for(Message can_message: set) {
                        MessagesWrapper temp = new MessagesWrapper(recieve_time - start_time + "", can_message);
                        messages.add(temp);
                        uniqueMessages.put(temp.getId(), temp);
                    }
                }
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
        messages = null;
        uniqueMessages = null;
        return  true;
    }

    @JavascriptInterface
    public String getMessages() {

        if(this.messages == null) {
            return "";
        }

        Gson gson = new Gson();
        synchronized (this.messages) {
//            List<MessagesWrapper> list = new ArrayList<MessagesWrapper>(uniqueMessages.values());
            return gson.toJson(messages);
        }
    }

    @JavascriptInterface
    public boolean isReading() {
        return messages != null;
    }

    @JavascriptInterface
    public String getSignalsById(String id) {
        MessagesWrapper messagesWrapper = (MessagesWrapper) uniqueMessages.get(id);
        return messagesWrapper.toJsonWithSignals();
    }

    @JavascriptInterface
    public String getDistribution(String id) {
        MessagesWrapper messagesWrapper = (MessagesWrapper) uniqueMessages.get(id);
        Map<String, ArrayList<Integer>> distribution = messagesWrapper.getSignalsDistribution();
        Gson gson = new Gson();
        return gson.toJson(distribution);
    }

    @JavascriptInterface
    public boolean saveCanSetting(String version, String speed, boolean is_open) {
        Log.i("save", version + " " + speed + " " + is_open);
        CanSettings canSettings = new CanSettings(can_setting);
        canSettings.setVersion(version);
        canSettings.setSpeed(speed);
        canSettings.setIs_open(is_open);
        return canSettings.save();
    }

    @JavascriptInterface
    public String loadCanSetting() {
        CanSettings canSettings = new CanSettings(can_setting);
        Map<String, String> result = new HashMap<>();
        result.put("version", canSettings.getVersion());
        result.put("speed", canSettings.getSpeed());
        result.put("is_open", canSettings.is_open() + "");
        return new Gson().toJson(result);
    }

    @JavascriptInterface
    public void sendCommand(String type, String value) {
        commandController.sendCommand(type, value);
    }

    @JavascriptInterface
    public String getCommandResult() {
        return commandController.getResult();
    }

    public ArrayList<MessagesWrapper> getMessagesForExport() {
        return messages;
    }

    @JavascriptInterface
    public String getLineDatas(String id) {
        if (messages == null) {
            return "";
        }

        ArrayList<MessagesWrapper> result = new ArrayList<>();
        synchronized (messages) {
            for (MessagesWrapper m : messages) {
                if (m.getId() == id) {
                    result.add(m);
                }
            }
        }

        Line line = new Line(id, result);

        Map<String, List> result_map = new HashMap<>();
        result_map.put("labels", line.getLables());
        result_map.put("datasets", line.egetDatas());
        return new Gson().toJson(result_map);
    }

}
