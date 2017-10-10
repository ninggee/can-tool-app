package engineering.software.advanced.cantoolapp.Converter.analyze;

import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanSignal;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public interface DataDecoding {

    //CAN信息解析
    public CanMessage messageDecoding(String message);

    //CAN信号解析
    public CanSignal signalDecoding(String signal);
}
