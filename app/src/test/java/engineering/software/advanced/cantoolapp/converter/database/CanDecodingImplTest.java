package engineering.software.advanced.cantoolapp.converter.database;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.database.Impl.CanDecodingImpl;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class CanDecodingImplTest {
    CanDecoding decoding = CanDecodingImpl.getInstance();
    @Test
    public void messageDecoding() throws Exception {
        CanMessage message = decoding.messageDecoding("BO_ 2148606241 Ext1: 8 Node_Body");
        System.out.println(message);
        System.out.println(message.getFrameType());
        System.out.println((long)1 << 31);
        System.out.println(String.format("%x %x", message.getId(), message.getId()));
    }

    @Test
    public void signalDecoding() throws Exception {
        CanSignal signal = decoding.signalDecoding("SG_ CDU_HVACFDefrostButtonStVD : 7|1@0+ (1,0) [0|1] \"\"  HVAC");
        System.out.println("SG_ CDU_HVACFDefrostButtonStVD : 7|1@0+ (1,0) [0|1] \"\"  HVAC");
        System.out.println(signal);
    }

}