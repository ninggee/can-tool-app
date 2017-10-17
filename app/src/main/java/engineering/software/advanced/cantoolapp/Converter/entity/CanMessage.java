package engineering.software.advanced.cantoolapp.Converter.entity;

import engineering.software.advanced.cantoolapp.Converter.enumeration.FrameType;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

//CAN信息
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

    public FrameType getFrameType() {
        //System.out.println(String.format("id: %d to Hex: %x msb: %d", id, id, id >> 31));
        long msb = id >> 31;
        if (msb == 1) {
            return FrameType.ExtensionFrame;
        }
        else if (msb == 0) {
            return FrameType.StandardFrame;
        }
        else {
            throw new RuntimeException("Id might be illegal.");
        }
    }

    public long getActualId() {
        if (getFrameType().equals(FrameType.ExtensionFrame)) {
            return id - ((long)1 << 31);
        }
        else {
            return id;
        }
    }

    public String toString() {
        return String.format(
                "CanMessage {\n\t%s\n\t%d\n\t%s\n\t%s\n\t%d\n\t%s\n}",
                bo, id, messageName, divide, dlc, nodeName
        );
    }
}
