package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.transmission.Impl.ReceiverImpl;
import engineering.software.advanced.cantoolapp.Converter.transmission.Receiver;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class ReceiverImplTest {
    @Test
    public void identifyType() throws Exception {

    }

    @Test
    public void parseYN() throws Exception {

    }

    @Test
    public void parseVersion() throws Exception {

    }

    @Test
    public void parseStandardFrame() throws Exception {
        Receiver receiver = new ReceiverImpl();
        System.out.println(receiver.parseStandardFrame("t12F4112233F40110\\r"));
    }

    @Test
    public void parseExtensionFrame() throws Exception {
        Receiver receiver = new ReceiverImpl();
        System.out.println(receiver.parseExtensionFrame("T1234567F81122334455667788\\r"));
    }

}