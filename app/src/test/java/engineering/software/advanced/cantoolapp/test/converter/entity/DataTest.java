package engineering.software.advanced.cantoolapp.test.converter.entity;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.entity.Data;
import engineering.software.advanced.cantoolapp.converter.enumeration.Endian;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class DataTest {
    @Test
    public void getData() throws Exception {
        Data data = new Data("F03245FF00348D");
        System.out.println(data.toString());
        System.out.println(String.format("%x", data.getByte(2)));
        System.out.println(data.getBit(17));
    }

    @Test
    public void testToString() throws Exception {
        Data data = new Data("F03245FF00348D1F");
        System.out.println(data.toString());
        System.out.println(String.format("%x", data.getByte(2)));
        System.out.println(data.getBit(17));
        data = new Data(3);
        System.out.println(data.toString());
    }

    @Test
    public void testGetSignal() throws Exception {
        Data data = new Data("F03245FF00348D1F");
        int signal = data.getSignal(0, 10, Endian.BIG_ENDIAN);
        System.out.println(signal);
    }

    @Test
    public void testGetByte() throws Exception {
        Data data = new Data("F03245FF00348D1F");
        int by = data.getByte(0);
        System.out.println(String.format("%x", by));
    }

    @Test
    public void testSetSignal() throws Exception {
        Data data = new Data(8);
        data.setSignal(1, 2, 3, Endian.BIG_ENDIAN);
        System.out.println(data);
    }
    @Test
    public void testSetBit() throws Exception {
        Data data = new Data(8);
        data.setBit(1, 1);
        System.out.println(data);
    }
}