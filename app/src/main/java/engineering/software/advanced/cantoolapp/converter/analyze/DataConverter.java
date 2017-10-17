package engineering.software.advanced.cantoolapp.converter.analyze;

import engineering.software.advanced.cantoolapp.converter.entity.Data;
import engineering.software.advanced.cantoolapp.converter.entity.Features;

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

    //在使用大端排序的情况下，计算出一些排列特征，便于之后的计算
    public Features getBigEndianFeatures(int start, int length);
}
