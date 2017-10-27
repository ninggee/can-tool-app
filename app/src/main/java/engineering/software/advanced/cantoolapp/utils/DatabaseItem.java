package engineering.software.advanced.cantoolapp.utils;

/**
 * Created by ningge on 2017/10/27.
 */

public class DatabaseItem {
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