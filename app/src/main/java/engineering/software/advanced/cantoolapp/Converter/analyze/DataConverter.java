package engineering.software.advanced.cantoolapp.Converter.analyze;

import engineering.software.advanced.cantoolapp.Converter.Entity.Data;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public interface DataConverter {

    //按照Motorola的Big Endian方式将data解析为signal
    public int bigEndianDecodeSignal(Data data, int start, int length);

    //按照Motorola的Big Endian方式将signal编入data。如果需要编入的位非零，则编码失败
    public boolean bigEndianEncodeSignal(Data data, int start, int length, int value);

    //使用Intel的Little Endian方式将data解析为signal
    public int littleEndianDecodeSignal(Data data, int start, int length);

    //按照Intel的Little Endian方式将signal编入data。如果需要编入的位非零，则编码失败
    public boolean littleEndianEncodeSignal(Data data, int start, int length, int value);

    //将信号值换算为变量值
    public double signalToValue(int signal, double a, double b);
}
