package engineering.software.advanced.cantoolapp.export.Impl;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.entity.Message;
import engineering.software.advanced.cantoolapp.export.Export;
import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

import static org.junit.Assert.*;

/**
 * Created by lhr on 2017/10/25.
 */
public class ExportImplTest {
    MessageAndSignalProcessor processor = new MessageAndSignalProcessor();
    @Test
    public void export() throws Exception {
        Message message = processor.decode("t03D19C");
        MessagesWrapper mw = new MessagesWrapper("123",message);
        MessagesWrapper mw2 = new MessagesWrapper("456",message);
        List<MessagesWrapper> list  = new ArrayList<>();
        list.add(mw);
        list.add(mw2);
        Export export = new ExportImpl();
        export.export("d:/","asd",".csv",list);
    }

}