package engineering.software.advanced.cantoolapp.Converter.entity;

import engineering.software.advanced.cantoolapp.Converter.enumeration.FrameDirection;
import engineering.software.advanced.cantoolapp.Converter.enumeration.FrameType;

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
    }
}
