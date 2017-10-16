package engineering.software.advanced.cantoolapp.test.Converter.entity;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.Entity.Data;
import engineering.software.advanced.cantoolapp.Converter.Enum.Endian;

import static org.junit.Assert.*;

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
}