package engineering.software.advanced.cantoolapp.webinterfaces;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leon.lfilepickerlibrary.LFilePicker;

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
import engineering.software.advanced.cantoolapp.converter.entity.Signal;
import engineering.software.advanced.cantoolapp.export.DataToFile;
import engineering.software.advanced.cantoolapp.export.Export;
import engineering.software.advanced.cantoolapp.export.Impl.DataToFileImpl;
import engineering.software.advanced.cantoolapp.export.Impl.ExportImpl;
import engineering.software.advanced.cantoolapp.utils.DatabaseItem;
import engineering.software.advanced.cantoolapp.converter.entity.Message;



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

    /**
     * init this interface
     * @param c context of main activity and can also treat as an activity object
     * @param connector connector instance
     * @param sharedPreferences storage can setting
     */
    public TestInterface(Context c, Connector connector, SharedPreferences sharedPreferences) {
        __context = c;
        __connector =connector;
        can_setting = sharedPreferences;
        commandController = new CommandController(__connector);
    }

    /**
     * show toast on the page
     * @param toast
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(__context, toast, Toast.LENGTH_LONG).show();
    }

    /**
     * get all paired bluetooth device
     * @return
     */
    @JavascriptInterface
    public String getDevices() {
        JSONObject result = new JSONObject(__connector.listAll());
        Log.d("interface", result.toString());
        return  result.toString();
    }

    /**
     * coonect to a bluetooth device
     * @param path
     * @return
     */
    @JavascriptInterface
    public boolean connect(String path) {
        return  __connector.connect(path, 115200);
    }

    /**
     * close connection to a device
     * @return
     */
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

    @JavascriptInterface
    public void importFile() {
        int REQUESTCODE_FROM_ACTIVITY = 1000;
        new LFilePicker()
                .withActivity((Activity) __context)
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                .withTitle("文件选择")
                .withMaxNum(1)
                .withNotFoundBooks("请选择一个文件")
                .withFileFilter(new String[]{"dbc"})
                .start();
    }

    @JavascriptInterface
    public String getAllDatabase() {
        engineering.software.advanced.cantoolapp.utils.Database database = new engineering.software.advanced.cantoolapp.utils.Database(
                __context.getSharedPreferences(engineering.software.advanced.cantoolapp.utils.Database.DATABASE_FILE_NAME, Context.MODE_PRIVATE)
        );

        return database.getAll();
    }

    @JavascriptInterface
    public boolean setDatabase(String name) {
        engineering.software.advanced.cantoolapp.utils.Database database = new engineering.software.advanced.cantoolapp.utils.Database(
                __context.getSharedPreferences(engineering.software.advanced.cantoolapp.utils.Database.DATABASE_FILE_NAME, Context.MODE_PRIVATE)
        );
        DatabaseItem now_using = database.getDefault();

        now_using.setIs_in_using(false);
        database.update(now_using.getName(), now_using);

        DatabaseItem new_use = database.getOne(name);
        new_use.setIs_in_using(true);

        database.update(new_use.getName(), new_use);

        processor.setDatabase(new_use.getPath());

        return true;
    }


    @JavascriptInterface
    public String getDefaultDatabase() {
        engineering.software.advanced.cantoolapp.utils.Database database = new engineering.software.advanced.cantoolapp.utils.Database(
                __context.getSharedPreferences(engineering.software.advanced.cantoolapp.utils.Database.DATABASE_FILE_NAME, Context.MODE_PRIVATE)
        );

        return database.getDefault().getName();
    }

    @JavascriptInterface
    public boolean removeDatabase(String name) {
        engineering.software.advanced.cantoolapp.utils.Database database = new engineering.software.advanced.cantoolapp.utils.Database(
                __context.getSharedPreferences(engineering.software.advanced.cantoolapp.utils.Database.DATABASE_FILE_NAME, Context.MODE_PRIVATE)
        );

        database.remove(name);

        return true;
    }

    @JavascriptInterface
    public void exportDatabase(String name, String format) {
        engineering.software.advanced.cantoolapp.utils.Database database = new engineering.software.advanced.cantoolapp.utils.Database(
                __context.getSharedPreferences(engineering.software.advanced.cantoolapp.utils.Database.DATABASE_FILE_NAME, Context.MODE_PRIVATE)
        );

        DatabaseItem item = database.getOne(name);
        DataToFile dataToFile = new DataToFileImpl();


        String temp = "";

        switch (format.toLowerCase()) {
            case "json":
                temp = dataToFile.dbToJson(item.getPath());
                break;
            case "xml":
                temp = dataToFile.dbToXml(item.getPath());
                break;
        }

        dataToFile.toFile(temp,
                "/storage/emulated/0/Android/data/engineering.software.advanced.cantoolapp/files/",
                item.getName(),
                "." + format
                );
    }


    @JavascriptInterface
    public String getDatabaseTreeInfo(String name) {
        engineering.software.advanced.cantoolapp.utils.Database database = new engineering.software.advanced.cantoolapp.utils.Database(
                __context.getSharedPreferences(engineering.software.advanced.cantoolapp.utils.Database.DATABASE_FILE_NAME, Context.MODE_PRIVATE)
        );

        DatabaseItem item = database.getOne(name);

        Database database1 = new DatabaseImpl(item.getPath());

        return database1.dbcTreeTojson();
    }


}
