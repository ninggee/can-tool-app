package engineering.software.advanced.cantoolapp.converter.entity;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/23.
 */
public class MessageTest {
    MessageAndSignalProcessor processor = new MessageAndSignalProcessor();
    @Test
    public void toJson() throws Exception {
        Message message = processor.decode("t03D19C");
        System.out.println(message.toJson());
    }

}