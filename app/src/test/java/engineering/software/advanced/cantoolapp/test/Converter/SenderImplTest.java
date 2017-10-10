package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.Sender;
import engineering.software.advanced.cantoolapp.Converter.SenderImpl;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */
public class SenderImplTest {
    private Sender sender = new SenderImpl();

    @Test
    public void requestVersion() throws Exception {
        System.out.println(sender.requestVersion());
    }

    @Test
    public void open() throws Exception {
        System.out.println(sender.open());
    }

    @Test
    public void setSpeed() throws Exception {
        for (int i = 0; i <= 8; i++) {
            System.out.println(sender.setSpeed(i));
        }
    }

    @Test
    public void close() throws Exception {
        System.out.println(sender.close());
    }

    @Test
    public void sendStandardFrame() throws Exception {
        System.out.println(sender.sendStandardFrame("123", "6644FF", "0000"));
    }

    @Test
    public void sendExtensionFrame() throws Exception {
        System.out.println(sender.sendExtensionFrame("12345678", "6644FF", "0000"));
    }

}