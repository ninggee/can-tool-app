package engineering.software.advanced.cantoolapp.exportFile;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.entity.Message;

import engineering.software.advanced.cantoolapp.exportFile.Impl.ExportImp;
import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by lhr on 2017/10/25.
 */

public class TestExport {
    MessageAndSignalProcessor processor = new MessageAndSignalProcessor();
    @Test
    public void export(){
        Message message = processor.decode("t03D19C");

        MessagesWrapper mw = new MessagesWrapper("123",message);

        ExportImp export = new ExportImp();
        List<MessagesWrapper> canList = new ArrayList<>();
        export.export("d:/","test",".csv",canList);
    }
}
