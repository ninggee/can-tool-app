package engineering.software.advanced.cantoolapp.Converter.Entity;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */
//解析后的信号
public class Signal {
    private String name;
    private double value;
    private int origin;
    private CanSignal canSignal;

    public Signal(String name, double value, int origin, CanSignal canSignal) {
        this.name = name;
        this.value = value;
        this.origin = origin;
        this.canSignal = canSignal;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public int getOrigin() {
        return origin;
    }

    public CanSignal getCanSignal() {
        return canSignal;
    }
}
