package engineering.software.advanced.cantoolapp.converter.database;

import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public interface CanDecoding {

    //CAN信息解析
    public CanMessage messageDecoding(String message);

    //CAN信号解析
    public CanSignal signalDecoding(String signal);
}
