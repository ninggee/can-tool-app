package engineering.software.advanced.cantoolapp.converter.entity;

import com.google.gson.Gson;
import java.util.Set;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */

//解析后的信息，其中包含多个信号
public class Message {
    private long id;
    private String name;
    private String nodeName;
    private CanMessage canMessage;
    private Data data;
    private Set<Signal> signals;

    public Message(long id, String name, String nodeName, CanMessage canMessage,Data data, Set<Signal> signals) {
        this.id = id;
        this.name = name;
        this.nodeName = nodeName;
        this.canMessage = canMessage;
        this.data = data;
        this.signals = signals;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNodeName() {
        return nodeName;
    }

    public CanMessage getCanMessage() {
        return canMessage;
    }

    public Data getData() {
        return data;
    }

    public Set<Signal> getSignals() {
        return signals;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
