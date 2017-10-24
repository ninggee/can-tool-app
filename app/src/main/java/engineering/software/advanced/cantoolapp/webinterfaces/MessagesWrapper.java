package engineering.software.advanced.cantoolapp.webinterfaces;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;

import engineering.software.advanced.cantoolapp.converter.entity.Message;

/**
 * Created by ningge on 2017/10/24.
 */

public class MessagesWrapper {
    private String time;
    private String id ;
    private String chn;
    private String name;
    private String dir;
    private String dlc;
    private String data;

    public MessagesWrapper(String time, Message message) {
        this.time = time;
        this.id = message.getId()  + "";
        this.chn = "";
        this.name = message.getName();
        this.dir = "";
        this.dlc = message.getCanMessage().getDlc() + "" ;
        this.data = message.getData().toHexString();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChn() {
        return chn;
    }

    public void setChn(String chn) {
        this.chn = chn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDlc() {
        return dlc;
    }

    public void setDlc(String dlc) {
        this.dlc = dlc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
