package engineering.software.advanced.cantoolapp.webinterfaces;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import engineering.software.advanced.cantoolapp.converter.entity.Signal;

/**
 * this is a wrapper to wrap message from send.html
 * Created by ningge on 2017/10/26.
 */


public class Message {
    private String id;
    private List<SignalMessage> values;
    private int period;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SignalMessage> getValues() {
        return values;
    }

    public void setValues(List<SignalMessage> values) {
        this.values = values;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}

class SignalMessage {
    private String name;
    private Double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
