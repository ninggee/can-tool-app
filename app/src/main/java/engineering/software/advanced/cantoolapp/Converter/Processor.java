package engineering.software.advanced.cantoolapp.Converter;

import java.util.Set;

import engineering.software.advanced.cantoolapp.Converter.entity.Message;
import engineering.software.advanced.cantoolapp.Converter.entity.Signal;

/**
 * Created by Zhang Dongdi on 2017/10/17.
 */

public interface Processor {

    //将接收到的总线数据一步步处理，最终变成信息信号
    public Message decode(String canMessageStr);

    //将message和signal编码，之后加上周期，得到can数据用于发送
    public String encode(long messageId, Set<Signal> signals, int period);
}
