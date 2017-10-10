package engineering.software.advanced.cantoolapp.Converter.transmission.Entity;

import engineering.software.advanced.cantoolapp.Converter.transmission.Enum.FrameDirection;
import engineering.software.advanced.cantoolapp.Converter.transmission.Enum.FrameType;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public class ExtensionFrame extends Frame {
    public ExtensionFrame(String raw, String id, String length, String data, String period,
                         FrameDirection direction) {
        this.raw = raw;
        this.id = id;
        this.length = length;
        this.data = data;
        this.period = period;
        this.type = FrameType.ExtensionFrame;
        this.direction = direction;

        //TODO 检查新建的扩展帧是否是合法的(不包括检查raw的转换是否正确）
    }
}
