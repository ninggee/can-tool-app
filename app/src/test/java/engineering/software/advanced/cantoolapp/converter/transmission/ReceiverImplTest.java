package engineering.software.advanced.cantoolapp.converter.transmission;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.transmission.Impl.ReceiverImpl;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class ReceiverImplTest {

    Receiver receiver = ReceiverImpl.getInstance();

    @Test
    public void identifyType() throws Exception {
        System.out.println(receiver.identifyType("T12F4112233F40110\\r"));
    }

    @Test
    public void parseYN() throws Exception {
        System.out.println(receiver.parseYN("\r"));
        System.out.println(receiver.parseYN("\007\007"));
    }

    @Test
    public void parseVersion() throws Exception {

    }

    @Test
    public void parseStandardFrame() throws Exception {
        System.out.println(receiver.parseStandardFrame("t12F4112233F40110\\r"));
    }
}