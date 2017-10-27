package engineering.software.advanced.cantoolapp.converter.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import engineering.software.advanced.cantoolapp.converter.enumeration.Endian;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

//CAN信号
public class CanSignal {

    //SG_
    private String sg;

    //字符串，最长32字节
    private String signalName;

    //分隔符，固定为 :
    private String divide;

    //起始位| bit长度@bit格式
    private String slt;

    //A:分辨率，1LSB的物理值精度，B：物理值的偏移量offset。
    //Phy=A*x+B, x为CAN信号的数值，phy为CAN信号对应的物理值
    private double a;
    private double b;

    //物理值的范围：Min=C到MAX=D
    private double c;
    private double d;

    //带有双引号的字符串，可以为空:””,
    private String unit;

    //接收该信号的节点Node名列表（也是ECU名）字符串，最长32字节。
    //如果多个ECU接收此信号，则用逗号将多了节点名隔开，例如：BCM,PEPS,ICM,CDU
    private String nodeName;

    public CanSignal() {

    }

    public CanSignal(String sg, String signalName, String divide, String slt,
                     double a, double b, double c, double d, String unit, String nodeName) {
        this.sg = sg;
        this.signalName = signalName;
        this.divide = divide;
        this.slt = slt;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.unit = unit;
        this.nodeName = nodeName;
    }

    public String getSg() {
        return sg;
    }

    public String getSignalName() {
        return signalName;
    }

    public String getDivide() {
        return divide;
    }

    public String getSlt() {
        return slt;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public String getUnit() {
        return unit;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Set<String> getNodes() {
        String[] strs = nodeName.split(",");
        Set<String> nodes = new HashSet<String>();
        for (String str : strs) {
            nodes.add(str);
        }
        return nodes;
    }

    public Endian getEndian() {
        String sym = slt.substring(slt.indexOf("@") + 1);
        if (sym.equals("0+")) {
            return Endian.BIG_ENDIAN;
        }
        else if (sym.equals("1+")) {
            return Endian.LITTLE_ENDIAN;
        }
        else if (sym.equals("0-")) {
            return Endian.ZERO_MINUS;
        }
        else if (sym.equals("1-")) {
            return Endian.ONE_MINUS;
        }
        else {
            throw new RuntimeException("The type is illegal.");
        }
    }

    public int getStart() {
        //System.out.println(slt);
        Pattern pattern = Pattern.compile(
                "^(\\d+)\\|\\d+@");
        Matcher matcher = pattern.matcher(slt);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        else {
            throw new RuntimeException("Error. Not Matched.");
        }
    }

    public int getLength() {
        Pattern pattern = Pattern.compile(
                "^\\d+\\|(\\d+)@");
        Matcher matcher = pattern.matcher(slt);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        else {
            throw new RuntimeException("Error. Not Matched.");
        }
    }

    public String toString() {
        return String.format(
                "CanSignal {\n\t%s\n\t%s\n\t%s\n\t%s\n\t%f\n\t%f\n\t%f\n\t%f\n\t%s\n\t%s\n}",
                sg, signalName, divide, slt, a, b, c, d, unit, nodeName
        );
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public void setDivide(String divide) {
        this.divide = divide;
    }

    public void setSlt(String slt) {
        this.slt = slt;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
