package engineering.software.advanced.cantoolapp.Converter.analyze;

import engineering.software.advanced.cantoolapp.Converter.Entity.Data;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public interface DataConverter {

    //按照Motorola的Big Endian方式将data解析为signal
    public int bigEndianConvertSignal(Data data, int start, int length);

    //使用Intel的Little Endian方式将data解析为signal
    public int littleEndianConvertSignal(Data data, int start, int length);

    public double signalToValue(int signal, int a, int b);
}
