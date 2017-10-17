package engineering.software.advanced.cantoolapp.Converter.database;

import engineering.software.advanced.cantoolapp.Converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.entity.CanSignal;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public interface CanEncoding {

    //CAN信息编码
    public String messageEncoding(CanMessage message);

    //CAN信号编码
    public String signalEncoding(CanSignal signal);
}
