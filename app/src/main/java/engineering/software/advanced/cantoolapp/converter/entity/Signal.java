package engineering.software.advanced.cantoolapp.converter.entity;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */
//解析后的信号
public class Signal {
    private String name;
    private double value;
    private CanSignal canSignal;

    public Signal(String name, double value, CanSignal canSignal) {
        this.name = name;
        this.value = value;
        this.canSignal = canSignal;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public CanSignal getCanSignal() {
        return canSignal;
    }
}
