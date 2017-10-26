package engineering.software.advanced.cantoolapp.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ningge on 2017/10/26.
 */

public class Database {
    private SharedPreferences storage;
    public static String DATABASE_FILE_NAME = "database_preference";

    public Database(SharedPreferences storage) {
        this.storage = storage;
    }

    public void add(DatabaseItem databaseItem) {
        SharedPreferences.Editor editor = storage.edit();
        editor.putString(databaseItem.getName(), new Gson().toJson(databaseItem));
        editor.commit();
    }

    public String getAll() {
        Map<String, ?> allMessages = storage.getAll();

        Map<String, DatabaseItem> result  = new HashMap<>();
        for(Object item : allMessages.values()) {
            DatabaseItem databaseItem = new Gson().fromJson((String)item, DatabaseItem.class);
            result.put(databaseItem.getName(), databaseItem);
        }
        return new Gson().toJson(result);
    }

    public String getOne(String name) {
        String result = storage.getString(name, "");
        return result;
    }
}

class DatabaseItem {
    private String name; //database name
    private String time; //database import time
    private boolean is_in_using; //if this database is being used
    private String path; //the path in where dbc file is located

    public DatabaseItem(String name, String time, boolean is_in_using, String path) {
        this.name = name;
        this.time = time;
        this.is_in_using = is_in_using;
        this.path = path;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIs_in_using() {
        return is_in_using;
    }

    public void setIs_in_using(boolean is_in_using) {
        this.is_in_using = is_in_using;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
