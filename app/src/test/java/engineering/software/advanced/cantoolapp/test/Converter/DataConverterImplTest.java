package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.Entity.Data;
import engineering.software.advanced.cantoolapp.Converter.analyze.DataConverter;
import engineering.software.advanced.cantoolapp.Converter.analyze.Impl.DataConverterImpl;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class DataConverterImplTest {
    DataConverter converter = new DataConverterImpl();

    @Test
    public void bigEndianConvertSignal() throws Exception {
        Data data = new Data("0011121314151617");
        System.out.println(data);
        System.out.println(data.getBit(17));
        int ans = converter.bigEndianConvertSignal(data, 8, 10);
        System.out.println(ans);
    }

    @Test
    public void littleEndianConvertSignal() throws Exception {

    }

    @Test
    public void signalToValue() throws Exception {

    }

}