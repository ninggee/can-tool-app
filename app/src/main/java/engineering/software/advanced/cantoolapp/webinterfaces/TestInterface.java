package engineering.software.advanced.cantoolapp.webinterfaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engineering.software.advanced.cantoolapp.communicator.ReaderThread;
import engineering.software.advanced.cantoolapp.communicator.handler.Handler;
import engineering.software.advanced.cantoolapp.connector.Connector;
import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.Processor;
import engineering.software.advanced.cantoolapp.converter.database.Database;
import engineering.software.advanced.cantoolapp.converter.database.Impl.DatabaseImpl;
import engineering.software.advanced.cantoolapp.converter.entity.Message;
import engineering.software.advanced.cantoolapp.converter.entity.Signal;
import engineering.software.advanced.cantoolapp.export.Export;
import engineering.software.advanced.cantoolapp.export.Impl.ExportImpl;

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
    ArrayList<MessagesWrapper> lastMessages = null;
    //store only most recently message for one id
    Map<String, MessagesWrapper> uniqueMessages = null;

    long start_time = new Date().getTime();
    ReaderThread reader = null;

    Processor processor = MessageAndSignalProcessor.getInstance();


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
        lastMessages = messages;
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
    public String getSignalsByIdAndTime(String id, String time) {
        for (MessagesWrapper m:
             messages) {
            if(m.getTime().equals(time) && m.getId().equals(id)) {
                return m.toJsonWithSignals();
            }
        }

        return "";
    }

    @JavascriptInterface
    public String getSignalByIdAndTimeAndName(String id, String time, String name) {
        for (MessagesWrapper m:
                messages) {
            if(m.getTime().equals(time) && m.getId().equals(id)) {
                for(Signal s : m.getSignals()) {
                    if(s.getName().equals(name)) {
                        return new Gson().toJson(s);
                    }
                }
            }
        }

        return "";
    }

    /**
     * get can signals distribution in can message
     * @param id
     * @return
     */
    @JavascriptInterface
    public String getDistribution(String id) {
        MessagesWrapper messagesWrapper = (MessagesWrapper) uniqueMessages.get(id);
        Map<String, ArrayList<Integer>> distribution = messagesWrapper.getSignalsDistribution();
        Gson gson = new Gson();
        return gson.toJson(distribution);
    }

    /**
     * sava setting to sharedPreference
     * @param version
     * @param speed
     * @param is_open
     * @return
     */
    @JavascriptInterface
    public boolean saveCanSetting(String version, String speed, boolean is_open) {
        Log.i("save", version + " " + speed + " " + is_open);
        CanSettings canSettings = new CanSettings(can_setting);
        canSettings.setVersion(version);
        canSettings.setSpeed(speed);
        canSettings.setIs_open(is_open);
        return canSettings.save();
    }

    /**
     * load can tool setting from sharedPreference
     * @return
     */
    @JavascriptInterface
    public String loadCanSetting() {
        CanSettings canSettings = new CanSettings(can_setting);
        Map<String, String> result = new HashMap<>();
        result.put("version", canSettings.getVersion());
        result.put("speed", canSettings.getSpeed());
        result.put("is_open", canSettings.is_open() + "");
        return new Gson().toJson(result);
    }

    /**
     * send command to can tool
     * @param type command type
     * @param value command value
     *
     */
    @JavascriptInterface
    public void sendCommand(String type, String value) {
        commandController.sendCommand(type, value);
    }

    //get result of last command to can tool
    @JavascriptInterface
    public String getCommandResult() {
        return commandController.getResult();
    }

    /**
     * export messages to xml
     * @return
     */
    @JavascriptInterface
    public void exportToCSV() {
        Export export = new ExportImpl();
        export.export("/storage/emulated/0/Android/data/engineering.software.advanced.cantoolapp/files/" ,
                "export_" + start_time,
                ".csv",
                lastMessages
                );
    }

    //this function provide data to draw line chart
    @JavascriptInterface
    public String getLineDatas(String id) {
        if (messages == null) {
            return "";
        }

        ArrayList<MessagesWrapper> result = new ArrayList<>();
        synchronized (messages) {
            for (MessagesWrapper m : messages) {
                if (m.getId().equals(id)) {
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

    @JavascriptInterface
    public String getAllMessages() {
        Database db = new DatabaseImpl();
        return db.searchAllMessage();
    }

    @JavascriptInterface
    public void sendMessage(String message) {
        Gson gson = new Gson();
        engineering.software.advanced.cantoolapp.webinterfaces.Message message1
                = gson.fromJson(message, engineering.software.advanced.cantoolapp.webinterfaces.Message.class);


        commandController = new CommandController(__connector);

        Map<String, Double> values = new HashMap<>();
        for(SignalMessage signalMessage: message1.getValues()) {
            values.put(signalMessage.getName(), signalMessage.getValue());
        }

        commandController.sendMessage(message1.getId(), values, message1.getPeriod());

    }

}
