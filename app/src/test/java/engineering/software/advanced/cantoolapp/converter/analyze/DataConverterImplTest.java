package engineering.software.advanced.cantoolapp.converter.analyze;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.analyze.Impl.DataConverterImpl;
import engineering.software.advanced.cantoolapp.converter.entity.Data;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class DataConverterImplTest {
    DataConverter converter = DataConverterImpl.getInstance();

    @Test
    public void bigEndianDecodeSignal() throws Exception {
        Data data = new Data("0011121314151617");
        System.out.println(data);
        System.out.println(data.getBit(17));
        int ans = converter.bigEndianDecodeSignal(data, 8, 13);
        System.out.println(ans);
        //TODO need more test
    }

    @Test
    public void bigEndianEncodeSignal() throws Exception {
        Data data = new Data("0000000000000000");
        System.out.println(data);
        boolean result = converter.bigEndianEncodeSignal(data, 9, 11, 2047);
        System.out.println("result: " + result);
        System.out.println(data);
    }

    @Test
    public void littleEndianDecodeSignal() throws Exception {

    }

    @Test
    public void littleEndianEncodeSignal() throws Exception {
        Data data = new Data("FF00000000000000");
        System.out.println(data);
        boolean result = converter.littleEndianEncodeSignal(data, 8, 8, 254);
        System.out.println("result: " + result);
        System.out.println(data);
    }

    @Test
    public void signalToValue() throws Exception {

    }

}