package engineering.software.advanced.cantoolapp.test.converter.database;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.database.CanEncoding;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;
import engineering.software.advanced.cantoolapp.converter.database.Impl.CanEncodingImpl;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class CanEncodingImplTest {
    CanEncoding encoding = CanEncodingImpl.getInstance();

    @Test
    public void messageEncoding() throws Exception {
        CanMessage message = new CanMessage("BO_", 856, "CDU_1", ":", 8, "CDU");
        System.out.println(encoding.messageEncoding(message));
    }

    @Test
    public void signalEncoding() throws Exception {
        CanSignal signal = new CanSignal("SG_", "CDU_HVACOffButtonSt", ":",
                "0|1@0+", 1, 0, 0, 1, "\"\"", "HVAC");
        System.out.println(encoding.signalEncoding(signal));
    }

}