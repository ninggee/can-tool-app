package engineering.software.advanced.cantoolapp.converter.entity;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */
//解析后的信号
public class Signal {
    private String name;
    private int origin;
    private double value;
    private CanSignal canSignal;

    public Signal(String name, int origin, double value, CanSignal canSignal) {
        this.name = name;
        this.origin = origin;
        this.value = value;
        this.canSignal = canSignal;
    }

    public String getName() {
        return name;
    }

    public int getOrigin() {
        return origin;
    }

    public double getValue() {
        return value;
    }

    public CanSignal getCanSignal() {
        return canSignal;
    }
}
