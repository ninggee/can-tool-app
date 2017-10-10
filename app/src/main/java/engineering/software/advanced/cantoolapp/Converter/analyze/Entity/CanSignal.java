package engineering.software.advanced.cantoolapp.Converter.analyze.Entity;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

//CAN信号实体
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

    public String toString() {
        return String.format(
                "CanMessage {\n\b%s\n\b%s\n\b%s\n\b%s\n\b%f\n\b%f\n\b%f\n\b%f\n\b%s\n\b%s\n}",
                sg, signalName, divide, slt, a, b, c, d, unit, nodeName
        );
    }
}
