package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.analyze.DataEncoding;
import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanSignal;
import engineering.software.advanced.cantoolapp.Converter.analyze.Impl.DataEncodingImpl;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class DataEncodingImplTest {
    @Test
    public void messageEncoding() throws Exception {
        DataEncoding encoding = new DataEncodingImpl();
        CanMessage message = new CanMessage("BO_", 856, "CDU_1", ":", 8, "CDU");
        System.out.println(encoding.messageEncoding(message));
    }

    @Test
    public void signalEncoding() throws Exception {
        DataEncoding encoding = new DataEncodingImpl();
        CanSignal signal = new CanSignal("SG_", "CDU_HVACOffButtonSt", ":",
                "0|1@0+", 1, 0, 0, 1, "\"\"", "HVAC");
        System.out.println(encoding.signalEncoding(signal));
    }

}