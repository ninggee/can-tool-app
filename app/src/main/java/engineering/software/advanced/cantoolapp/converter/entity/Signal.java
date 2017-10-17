package engineering.software.advanced.cantoolapp.converter.entity;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */
//解析后的信号
public class Signal {
    private String name;
    private double value;

    public Signal(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
