package engineering.software.advanced.cantoolapp.Converter.Entity;

import java.util.Set;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */
//解析后的信号
public class Signal {
    private String name;
    private double value;
    private int origin;
    private double min;
    private double max;
    private Set<String> nodes;

    public Signal(String name, double value, int origin, double min, double max, Set<String> nodes) {
        this.name = name;
        this.value = value;
        this.origin = origin;
        this.min = min;
        this.max = max;
        this.nodes = nodes;
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

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public Set<String> getNodes() {
        return nodes;
    }
}
