package engineering.software.advanced.cantoolapp.Converter.transmission;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public interface Sender {

    //请求获得ConTool装置的版本信息
    public String requestVersion();

    //请求CanTool装置打开电源
    public String open();

    /*
    设置串口的通信速率，n为1 - 8的数字，分别表示8个不同的速率：
    S0 Setup 10Kbit
    S1 Setup 20Kbit
    S2 Setup 50Kbit
    S3 Setup 100Kbit
    S4 Setup 125Kbit
    S5 Setup 250Kbit
    S6 Setup 500Kbit
    S7 Setup 800Kbit
    S8 Setup 1Mbit
    */
    public String setSpeed(int n);

    //请求CanTool装置关闭电源
    public String close();

    //向CanTool装置发送CAN标准帧命令（以t开头）
    public String sendStandardFrame(String id, String value, String period);

    //向CanTool装置发送CAN扩展帧命令（以T开头）
    public String sendExtensionFrame(String id, String value, String period);

    //发送函数
    public boolean send(String command);
}
