package engineering.software.advanced.cantoolapp.converter;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.entity.Message;
import engineering.software.advanced.cantoolapp.converter.entity.Signal;

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
            System.out.println(String.format("signal: %s, value: %f", signal.getName(), signal.getValue()));
        }
    }

    @Test
    public void decodeMultiple() throws  Exception {
        Set<Message> messages = processor.decodeMultiple("t03D19C\\rt03D19D\\r");
        System.out.println("message number: " + messages.size());
        for (Message message : messages) {
            System.out.println("message: " + message.getName());
            for (Signal signal : message.getSignals()) {
                System.out.println(String.format("\tsignal: %s, value: %f", signal.getName(), signal.getValue()));
            }
        }
    }

    @Test
    public void encode() throws Exception {
        Message message = processor.decode("t03D19C");
        Set<Signal> signals = new HashSet<Signal>();
        signals.add(new Signal("CDU_HVACACCfg", 1, null));
        signals.add(new Signal("CDU_HVACAirCirCfg", 2, null));
        signals.add(new Signal("CDU_HVACComfortCfg", 3, null));
        String encodedMessage = processor.encode(61, signals, 255);
        System.out.println("result: " + encodedMessage);
    }

}