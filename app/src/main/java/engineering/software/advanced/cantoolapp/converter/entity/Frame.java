package engineering.software.advanced.cantoolapp.converter.entity;

import engineering.software.advanced.cantoolapp.converter.enumeration.FrameDirection;
import engineering.software.advanced.cantoolapp.converter.enumeration.FrameType;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public abstract class Frame {

    //原字符串
    protected String raw;

    //帧id
    protected String id;

    //data字节数
    protected String length;

    //发送的信息
    protected String data;

    //发送周期（毫秒）
    protected String period;

    //帧类型
    protected FrameType type;

    //发送方向
    protected FrameDirection direction;

    public String getRaw() {
        return raw;
    }

    public String getId() {
        return id;
    }

    public String getLength() {
        return length;
    }

    public String getData() {
        return data;
    }

    public String getPeriod() {
        return period;
    }

    public FrameType getType() {
        return type;
    }

    public FrameDirection getDirection() {
        return direction;
    }

    public String toString() {
        return String.format("%s {\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n}",
                type.toString(), raw, id,length, data, period, direction.toString());
    }
}
