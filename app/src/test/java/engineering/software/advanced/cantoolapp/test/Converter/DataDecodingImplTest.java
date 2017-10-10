package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanSignal;
import engineering.software.advanced.cantoolapp.Converter.analyze.Impl.DataDecodingImpl;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */
public class DataDecodingImplTest {
    DataDecodingImpl decoding = new DataDecodingImpl();
    @Test
    public void messageDecoding() throws Exception {
        CanMessage message = decoding.messageDecoding("BO_ 2148606241 Ext1: 8 Node_Body");
        System.out.println(message);
    }

    @Test
    public void signalDecoding() throws Exception {
        CanSignal signal = decoding.signalDecoding("SG_ CDU_HVACFDefrostButtonStVD : 7|1@0+ (1,0) [0|1] \"\"  HVAC");
        System.out.println("SG_ CDU_HVACFDefrostButtonStVD : 7|1@0+ (1,0) [0|1] \"\"  HVAC");
        System.out.println(signal);
    }

}