package engineering.software.advanced.cantoolapp.export.Impl;

import org.junit.Test;

import java.io.FileInputStream;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.database.Impl.CanMessageUnionSignal;
import engineering.software.advanced.cantoolapp.export.FileToData;

/**
 * Created by Zhang Dongdi on 2017/10/27.
 */
public class FileToDataImplTest {
    private FileToData fileToData = FileToDataImpl.getInstance();

    @Test
    public void xmlToCanMessageUnionSignal() throws Exception {
        Set<CanMessageUnionSignal> unions = fileToData.xmlToCanMessageUnionSignal(new FileInputStream("F:/db.xml"));

        for (CanMessageUnionSignal union : unions) {
            System.out.println(union);
        }
    }

    @Test
    public void jsonToCanMessageUnionSignal() throws Exception {

    }

}