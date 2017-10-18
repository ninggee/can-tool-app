package engineering.software.advanced.cantoolapp.converter.entity;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.database.CanDecoding;
import engineering.software.advanced.cantoolapp.converter.database.Impl.CanDecodingImpl;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */
public class CanSignalTest {
    CanDecoding canDecoding = CanDecodingImpl.getInstance();
    CanSignal canSignal = canDecoding.signalDecoding("SG_ ESC_VehSpd : 36|13@0+ (0.05625,0) [0|240] \"\"  BCM,PEPS,ICM,AVM,CDU");
    @Test
    public void getEndian() throws Exception {

    }

    @Test
    public void getStart() throws Exception {
        System.out.println(canSignal.getStart());
    }

    @Test
    public void getLength() throws Exception {
        System.out.println(canSignal.getLength());
    }

    @Test
    public void getNodes() throws Exception {
        for (String node : canSignal.getNodes()) {
            System.out.println(node);
        }
    }

}