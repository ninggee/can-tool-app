package engineering.software.advanced.cantoolapp.webinterfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.internal.Streams;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.Processor;
import engineering.software.advanced.cantoolapp.converter.analyze.DataConverter;
import engineering.software.advanced.cantoolapp.converter.analyze.Impl.DataConverterImpl;
import engineering.software.advanced.cantoolapp.converter.entity.Features;
import engineering.software.advanced.cantoolapp.converter.entity.Message;
import engineering.software.advanced.cantoolapp.converter.entity.Signal;
import engineering.software.advanced.cantoolapp.converter.enumeration.Endian;

/**
 * Created by ningge on 2017/10/24.
 */

public class MessagesWrapper {
    @Expose private String time;
    @Expose private String id ;
    @Expose private String chn;
    @Expose private String name;
    @Expose private String dir;
    @Expose private String dlc;
    @Expose private String data;
    private Set<Signal> signals;


    public MessagesWrapper(String time, Message message) {
        this.time = time;
        this.id = message.getId()  + "";
        this.chn = "";
        this.name = message.getName();
        this.dir = "";
        this.dlc = message.getCanMessage().getDlc() + "" ;
        this.data = message.getData().toHexString();
        this.signals = message.getSignals();
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

    public Set<Signal> getSignals() {
        return signals;
    }

    public void setSignals(Set<Signal> signals) {
        this.signals = signals;
    }

    public Map<String, ArrayList<Integer>> getSignalsDistribution() {
        DataConverter dataConverter = DataConverterImpl.getInstance();

        Map<String, ArrayList<Integer>> result = new HashMap<>();

        for(Signal signal: signals) {
            Endian endian = signal.getCanSignal().getEndian();
            int start = signal.getCanSignal().getStart();
            int length = signal.getCanSignal().getLength();
            ArrayList<Integer> temp = new ArrayList<>();

            switch (endian) {
                case BIG_ENDIAN:
                    Features features = dataConverter.getBigEndianFeatures(start, length);
                    if(features.isHasFirst()) {
                        for(int i = features.getFirstLow(); i <= features.getFirstHigh(); i++) {
                            temp.add(i);
                        }
                    }

                    if(features.isHasMid()) {
                        for(int i = features.getMidStartByte(); i <= features.getMidEndByte(); i++) {
                            for(int j = i * 8; j < i * 8 + 8; j++ ) {
                                temp.add(j);
                            }
                        }
                    }
                    if(features.isHasLast()) {
                        for(int i = features.getLastLow(); i <= features.getLastHigh(); i++) {
                            temp.add(i);
                        }
                    }


                    break;
                case LITTLE_ENDIAN:
                   for(int i = start; i < length + start; i++) {
                       temp.add(i);
                   }
                   break;
            }
            result.put(signal.getName(), temp);
        }
        return result;
    }

    //this method will return json exclude signals field
    public String toJson() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

    public String toJsonWithSignals() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
