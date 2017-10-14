package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.Entity.Message;
import engineering.software.advanced.cantoolapp.Converter.Entity.Signal;
import engineering.software.advanced.cantoolapp.Converter.MessageAndSignalProcessor;

/**
 * Created by Zhang Dongdi on 2017/10/14.
 */
public class MessageAndSignalProcessorTest {
    MessageAndSignalProcessor processor = new MessageAndSignalProcessor();
    @Test
    public void decode() throws Exception {
        Message message = processor.decode("t03D19C");
        System.out.println(message.getId());
        System.out.println(message.getSignals().size());
        for (Signal signal : message.getSignals()) {
            System.out.println(String.format("signal: %s, origin: %x, value: %f", signal.getName(), signal.getOrigin(), signal.getValue()));
        }
    }

}