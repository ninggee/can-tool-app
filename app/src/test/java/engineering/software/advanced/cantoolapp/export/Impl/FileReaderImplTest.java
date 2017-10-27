package engineering.software.advanced.cantoolapp.export.Impl;

import org.junit.Test;

import java.io.FileInputStream;

import engineering.software.advanced.cantoolapp.export.FileReader;

import static org.junit.Assert.*;

/**
 * Created by Zhang Dongdi on 2017/10/27.
 */
public class FileReaderImplTest {
    private FileReader fileReader = FileReaderImpl.getInstance();

    @Test
    public void xmlToCanMessageUnionSignal() throws Exception {
        fileReader.xmlToCanMessageUnionSignal(new FileInputStream("F:/db.xml"));
    }

    @Test
    public void jsonToCanMessageUnionSignal() throws Exception {

    }

}