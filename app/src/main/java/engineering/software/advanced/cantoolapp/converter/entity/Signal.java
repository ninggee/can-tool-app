package engineering.software.advanced.cantoolapp.converter.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */
//解析后的信号
@XmlRootElement
public class Signal {
    private String name;
    private String origin;
    private double value;
    private CanSignal canSignal;

    public Signal() {

    }

    public Signal(String name, String origin, double value, CanSignal canSignal) {
        this.name = name;
        this.origin = origin;
        this.value = value;
        this.canSignal = canSignal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public CanSignal getCanSignal() {
        return canSignal;
    }

    public void setCanSignal(CanSignal canSignal) {
        this.canSignal = canSignal;
    }
}
