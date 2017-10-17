package engineering.software.advanced.cantoolapp.Converter.database;

import engineering.software.advanced.cantoolapp.Converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.entity.CanSignal;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public interface CanDecoding {

    //CAN信息解析
    public CanMessage messageDecoding(String message);

    //CAN信号解析
    public CanSignal signalDecoding(String signal);
}
