package engineering.software.advanced.cantoolapp.Converter.Entity;

import engineering.software.advanced.cantoolapp.Converter.Enum.FrameDirection;
import engineering.software.advanced.cantoolapp.Converter.Enum.FrameType;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public class StandardFrame extends Frame {
    public StandardFrame(String raw, String id, String length, String data, String period,
                         FrameDirection direction) {
        this.raw = raw;
        this.id = id;
        this.length = length;
        this.data = data;
        this.period = period;
        this.type = FrameType.StandardFrame;
        this.direction = direction;

        //TODO 检查新建的标准帧是否是合法的
    }
}
