package engineering.software.advanced.cantoolapp.Converter.transmission.Entity;

import engineering.software.advanced.cantoolapp.Converter.transmission.Enum.FrameDirection;
import engineering.software.advanced.cantoolapp.Converter.transmission.Enum.FrameType;

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
}
