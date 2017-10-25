package engineering.software.advanced.cantoolapp.webinterfaces;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.entity.Message;

import static org.junit.Assert.*;

/**
 * Created by ningge on 2017/10/25.
 */
public class LineDataTest {
    MessageAndSignalProcessor processor = new MessageAndSignalProcessor();
    Message message;
    ArrayList<MessagesWrapper> messagesWrappers;

    @Before
    public void initialize() {
        message= processor.decode("t03D19C");

        MessagesWrapper m1 = new MessagesWrapper("123", message);
        MessagesWrapper m2 = new MessagesWrapper("346", message);
        MessagesWrapper m3 = new MessagesWrapper("789", message);

        messagesWrappers = new ArrayList<>();
        messagesWrappers.add(m1);
        messagesWrappers.add(m2);
        messagesWrappers.add(m3);
    }
    @Test
    public void getData() throws Exception {
        Line line = new Line(message.getName(), messagesWrappers);
        String expected = "[\"123\",\"346\",\"789\"]";
//        assertEquals(expected, line.getLables());
        System.out.println(line.getDatas());

    }

    @Test
    public void getLabel() throws Exception {
        Line line = new Line(message.getName(), messagesWrappers);
        String expected = "[\"123\",\"346\",\"789\"]";
        assertEquals(expected, line.getLables());
    }
}