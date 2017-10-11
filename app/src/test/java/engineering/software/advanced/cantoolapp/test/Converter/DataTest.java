package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.Entity.Data;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */
public class DataTest {
    @Test
    public void getData() throws Exception {
        Data data = new Data("FFF0981442145612");
        for (int i = 0; i < data.getData().length; i++) {
            System.out.println(data.getData()[i]);
            for (int j = 0; j < 8; j++) {
                System.out.println("\t" + data.getBit(i * 8 + j));
            }
        }
    }

}