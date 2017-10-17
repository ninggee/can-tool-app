package engineering.software.advanced.cantoolapp.converter.entity;

import engineering.software.advanced.cantoolapp.converter.enumeration.FrameDirection;
import engineering.software.advanced.cantoolapp.converter.enumeration.FrameType;

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
    }
}
