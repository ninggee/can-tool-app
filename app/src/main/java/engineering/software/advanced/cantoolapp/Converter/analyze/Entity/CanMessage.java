package engineering.software.advanced.cantoolapp.Converter.analyze.Entity;

import java.text.MessageFormat;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

//CAN信息实体
public class CanMessage {

    //BO_
    private String bo;

    //msb=1表示扩展帧，0表示标准帧
    private long id;

    //字符串，最长32字节
    private String messageName;

    //固定为 :
    private String divide;

    //范围:0—8, 表示此CAN信息的DATA的长度为8byte
    private int dlc;

    //字符串，最长32字节,发送此信息的Node名。也是ECU名
    private String nodeName;

    public CanMessage(String bo, long id, String messageName,
                      String divide, int dlc, String nodeName) {
        this.bo = bo;
        this.id = id;
        this.messageName = messageName;
        this.divide = divide;
        this.dlc = dlc;
        this.nodeName = nodeName;
    }

    public String getBo() {
        return bo;
    }

    public long getId() {
        return id;
    }

    public String getMessageName() {
        return messageName;
    }

    public String getDivide() {
        return divide;
    }

    public int getDlc() {
        return dlc;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String toString() {
        return String.format(
                "CanMessage {\n\b%s\n\b%d\n\b%s\n\b%s\n\b%d\n\b%s\n}",
                bo, id, messageName, divide, dlc, nodeName
        );
    }
}
