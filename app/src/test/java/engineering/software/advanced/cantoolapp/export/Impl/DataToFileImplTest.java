package engineering.software.advanced.cantoolapp.export.Impl;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.Processor;
import engineering.software.advanced.cantoolapp.converter.entity.Message;
import engineering.software.advanced.cantoolapp.export.DataToFile;
import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/25.
 */
public class DataToFileImplTest {
    DataToFile dataToFile = new DataToFileImpl();
    Processor processor = new MessageAndSignalProcessor();
    @Test
    public void toXml() throws Exception {
        Set<Message> messages = processor.decodeMultiple("t03D19C\rt03D19D\r");
        List<MessagesWrapper> wrappers = new ArrayList<>();
        for (Message message : messages) {
            wrappers.add(new MessagesWrapper("123", message));
        }
        System.out.println(dataToFile.toXml(wrappers));
    }

    @Test
    public void toFile() throws Exception {

    }

}