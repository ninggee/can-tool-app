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
        return new Gson().toJson(result.values());
    }

    public DatabaseItem getOne(String name) {
        String result = storage.getString(name, "");
        return  new Gson().fromJson(result, DatabaseItem.class);
    }

    public void remove(String name) {
        SharedPreferences.Editor editor = storage.edit();
        editor.remove(name);
        editor.commit();
    }

    public DatabaseItem getDefault() {
        Map<String, ?> allMessages = storage.getAll();

        Map<String, DatabaseItem> result  = new HashMap<>();
        for(Object item : allMessages.values()) {
            DatabaseItem databaseItem = new Gson().fromJson((String)item, DatabaseItem.class);
            if(databaseItem.isIs_in_using()) {
                return databaseItem;
            }
        }

        return null;
    }

    public void update(String name , DatabaseItem newData) {
        SharedPreferences.Editor editor = storage.edit();

        editor.putString(name, new Gson().toJson(newData));
        editor.commit();
    }


}

