package engineering.software.advanced.cantoolapp.test.Converter.entity;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.Entity.Data;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class DataTest {
    @Test
    public void getData() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        Data data = new Data("F03245FF00348D1A");
        System.out.println(data.toString());
        System.out.println(String.format("%x", data.getByte(2)));
        System.out.println(data.getBit(17));
    }

}