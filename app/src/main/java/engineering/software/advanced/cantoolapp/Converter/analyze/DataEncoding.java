package engineering.software.advanced.cantoolapp.Converter.analyze;

import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanSignal;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public interface DataEncoding {

    //CAN信息编码
    public String messageEncoding(CanMessage message);

    //CAN信号编码
    public String signalEncoding(CanSignal signal);
}
